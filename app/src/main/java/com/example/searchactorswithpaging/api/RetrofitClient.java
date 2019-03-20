package com.example.searchactorswithpaging.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    String BASE_URL = "https://api.themoviedb.org/3/" ;

    private static com.example.searchactorswithpaging.api.RetrofitClient mInstance;
    private Retrofit retrofit;


    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized com.example.searchactorswithpaging.api.RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new com.example.searchactorswithpaging.api.RetrofitClient();
        }
        return mInstance;
    }

    public TMDBRequest getService() {
        return retrofit.create(TMDBRequest.class);
    }
}