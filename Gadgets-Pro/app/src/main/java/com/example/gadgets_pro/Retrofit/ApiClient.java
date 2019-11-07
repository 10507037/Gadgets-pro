package com.myapp.ytvideos.Retrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lenovo on 6/4/2018.
 */

public class ApiClient implements Serializable {

//    public static final String BASE_URL="http://104.248.230.106:7000/api/v1/";   //default id used by all virtual devices .i.e emulator

    public static final String BASE_URL = "http://192.168.2.5:3000/";
    public static Retrofit retrofit =null;

    public static Retrofit getApiClient()  //returns an instance of retrofit
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