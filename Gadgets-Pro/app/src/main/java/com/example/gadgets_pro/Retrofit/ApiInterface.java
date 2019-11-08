package com.myapp.ytvideos.Retrofit;

import com.myapp.ytvideos.Models.VideoModel;
import com.myapp.ytvideos.SearchModel.Item;
import com.myapp.ytvideos.SearchModel.SearchModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Lenovo on 10/11/2018.
 */

public interface ApiInterface {

    @Headers("Content-Type: application/json")
    @GET("getlinks")
    Call<VideoModel> getLinks();

    @Headers({"Content-Type: application/json"})
    @GET("search?")
    Call<SearchModel> getSearchQueries(@Query("part") String part, @Query("q") String searchTerm,
                                @Query("maxResults") String maxResults,
                                @Query("key") String apiKey);



}





