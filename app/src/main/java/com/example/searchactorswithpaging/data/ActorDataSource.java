package com.example.searchactorswithpaging.data;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.example.searchactorswithpaging.api.RetrofitClient;
import com.example.searchactorswithpaging.model.Actor;
import com.example.searchactorswithpaging.model.ResultsPage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActorDataSource extends PageKeyedDataSource<Integer, Actor> {

    private static final String API_KEY = "90f9f56e299e21f06338b0197a5ff6f6" ;

    private static final int FIRST_PAGE = 1 ;


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull final LoadInitialCallback<Integer, Actor> callback) {
        RetrofitClient.getInstance()
                .getService().getPopularResults(API_KEY, FIRST_PAGE)
                .enqueue(
                    new Callback<ResultsPage>() {
                        @Override
                        public void onResponse(Call<ResultsPage> call, Response<ResultsPage> response) {
                            if (response.body() != null )
                                callback.onResult(response.body().getResults(),
                                        null, FIRST_PAGE + 1);
                        }

                        @Override
                        public void onFailure(Call<ResultsPage> call, Throwable t) {
                            // handle
                        }
                    }
                ) ;
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params,
                           @NonNull final LoadCallback<Integer, Actor> callback) {
        RetrofitClient.getInstance()
                .getService().getPopularResults(API_KEY, params.key)
                .enqueue(
                    new Callback<ResultsPage>() {
                        @Override
                        public void onResponse(Call<ResultsPage> call, Response<ResultsPage> response) {
                            Integer adjacentPageKey = (params.key > 1) ? params.key -1 : null ;

                            if (response.body() != null )
                                callback.onResult(response.body().getResults(), adjacentPageKey) ;
                        }

                        @Override
                        public void onFailure(Call<ResultsPage> call, Throwable t) {
                            // handle
                        }
                    }
                ) ;

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params,
                          @NonNull final LoadCallback<Integer, Actor> callback) {
        RetrofitClient.getInstance()
                .getService().getPopularResults(API_KEY, params.key)
                .enqueue(
                    new Callback<ResultsPage>() {
                        @Override
                        public void onResponse(Call<ResultsPage> call, Response<ResultsPage> response) {

                            if (response.body() != null ) {
                                Integer key = ( params.key < response.body().getTotalPages() )?
                                                params.key + 1 : null ;

                                callback.onResult(response.body().getResults(), key) ;
                            }
                        }

                        @Override
                        public void onFailure(Call<ResultsPage> call, Throwable t) {
                            // handle
                        }
                    }
                ) ;

    }
}
