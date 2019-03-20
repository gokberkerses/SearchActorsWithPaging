package com.example.searchactorswithpaging.api;

import com.example.searchactorswithpaging.model.ResultsPage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDBRequest {

    @GET("person/popular")
    Call<ResultsPage> getPopularResults(
            @Query("api_key") String apiKey,
            @Query("page") int queriedPage
    ) ;

    @GET("search/person")
    Call<ResultsPage> getSearchResults(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("page") int queriedPage
    ) ;
}
