package com.myapp.ytvideos;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.myapp.ytvideos.Adapters.AdapterListVideo;
import com.myapp.ytvideos.Models.Link;
import com.myapp.ytvideos.Models.VideoModel;
import com.myapp.ytvideos.Retrofit.ApiClient;
import com.myapp.ytvideos.Retrofit.ApiInterface;
import com.myapp.ytvideos.Retrofit.SearchClient;
import com.myapp.ytvideos.SearchModel.Item;
import com.myapp.ytvideos.SearchModel.SearchModel;
import com.myapp.ytvideos.Utils.SpacingItemDecoration;
import com.myapp.ytvideos.Utils.Tools;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;


    private AdapterListVideo adapter;

    ArrayList<Item> arrayListSearchModel;
    public ArrayList<Link> videoArrayListServer;

    ApiInterface apiInterface;

    ArrayList<Link> serverArrayListCopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        serverArrayListCopy = new ArrayList<>();

        arrayListSearchModel = new ArrayList<>();
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        swipeRefreshLayout.setOnRefreshListener(this);
        videoArrayListServer = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpacingItemDecoration(1,
                Tools.dip2px(this, 10), true));


        getRequestFromServer();
    }






    private void getRequestFromServer()
    {
        Call<VideoModel> call = apiInterface.getLinks();

        call.enqueue(new Callback<VideoModel>() {
            @Override
            public void onResponse(Call<VideoModel> call, Response<VideoModel> response) {
                VideoModel videoModel = response.body();

                videoArrayListServer.clear();
                if(videoModel!=null && videoModel.getLinks().size()>0)
                {
                    for(int i=0;i<videoModel.getLinks().size();i++)
                    {
                        videoArrayListServer.add(videoModel.getLinks().get(i));
                    }

                    serverArrayListCopy.clear();
                    serverArrayListCopy.addAll(videoArrayListServer);
                    swipeRefreshLayout.setRefreshing(false);
                    adapter = new AdapterListVideo(MainActivity.this,videoArrayListServer);
                    adapter.setAdapterType(false);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
                else
                {
                    // no data found


                    swipeRefreshLayout.setRefreshing(false);


                }

            }

            @Override
            public void onFailure(Call<VideoModel> call, Throwable t) {
                adapter = new AdapterListVideo(MainActivity.this,videoArrayListServer);
                adapter.setAdapterType(false);
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(),"Could not connect to the server.",Toast.LENGTH_LONG).show();
            }
        });

    }



    public void getVideosFromYoutube(String s)
    {

        apiInterface= SearchClient.getSearchClient().create(ApiInterface.class);
        Call<SearchModel> callSearch = apiInterface.getSearchQueries("snippet",s,"30","AIzaSyDuoMZW3x-Vz1I5NhfeI5SgiZJEaTuxIl4");

        callSearch.enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                SearchModel searchModel = response.body();

                if(searchModel!=null)
                {
                    arrayListSearchModel.clear();
                    arrayListSearchModel.addAll(searchModel.getItems());
                    videoArrayListServer.clear();

                    for(int i=0;i<arrayListSearchModel.size();i++)
                    {
                        if(arrayListSearchModel.get(i).getId().getVideoId()!=null)
                        {
                            Link link = new Link();
                            link.setTitle(arrayListSearchModel.get(i).getSnippet().getTitle());
                            link.setThumbnail(arrayListSearchModel.get(i).getSnippet().getThumbnails().getHigh().getUrl());
                            link.setVideoId(arrayListSearchModel.get(i).getId().getVideoId());
                            videoArrayListServer.add(link);
                        }

                    }

                   adapter = new AdapterListVideo(MainActivity.this,videoArrayListServer);
                    recyclerView.setAdapter(adapter);

                    swipeRefreshLayout.setRefreshing(false);
                    adapter.setAdapterType(true);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this,""+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    String searchTerm;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);


        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                adapter = new AdapterListVideo(MainActivity.this, serverArrayListCopy);
                recyclerView.setAdapter(adapter);
                adapter.setAdapterType(false);
                swipeRefreshLayout.setRefreshing(false);
                adapter.notifyDataSetChanged();

                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchTerm =s;
                ArrayList<Link> filteredList = new ArrayList<>();
                for (Link link : serverArrayListCopy) {

                    if (link.getTitle().toLowerCase().contains(s.toLowerCase())) {
                        filteredList.add(link);
                    }
                }

                if(filteredList.size()>0)
                {
                    swipeRefreshLayout.setRefreshing(false);
                    adapter = new AdapterListVideo(MainActivity.this,filteredList);
                    recyclerView.setAdapter(adapter);
                    adapter.setAdapterType(false);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    swipeRefreshLayout.setRefreshing(false);
                    getVideosFromYoutube(s);

                 //   Toast.makeText(getApplicationContext(),"Youtube API request",Toast.LENGTH_LONG).show();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onRefresh() {
        if(adapter!=null)
        {
            if(adapter.getAdapterType())
            {
                getVideosFromYoutube(searchTerm);
            }
            else
            {
                getRequestFromServer();
        }
        }

    }

    @Override
    public void onBackPressed() {

        if(adapter.getAdapterType())
        {
            adapter = new AdapterListVideo(this, serverArrayListCopy);
            recyclerView.setAdapter(adapter);
            adapter.setAdapterType(false);
            adapter.notifyDataSetChanged();
        }

        else
        {

            super.onBackPressed();
        }


    }
}
