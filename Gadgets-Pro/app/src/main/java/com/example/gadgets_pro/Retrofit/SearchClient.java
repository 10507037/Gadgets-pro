package com.myapp.ytvideos.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchClient
{
    public static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";
    public static Retrofit retrofit =null;

    public static Retrofit getSearchClient()  //returns an instance of retrofit
    {
        if(retrofit==null)
        {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit=new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
