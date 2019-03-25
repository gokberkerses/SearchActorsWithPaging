package com.example.searchactorswithpaging.data;

import android.arch.paging.PageKeyedDataSource;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;
import android.widget.Toast;

import com.example.searchactorswithpaging.R;
import com.example.searchactorswithpaging.SearchActivity;
import com.example.searchactorswithpaging.api.RetrofitClient;
import com.example.searchactorswithpaging.api.TMDBRequest;
import com.example.searchactorswithpaging.model.Actor;
import com.example.searchactorswithpaging.model.ResultsPage;
import com.example.searchactorswithpaging.signal.CountCallback;
import com.example.searchactorswithpaging.signal.CountNotifier;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class ActorDataSource extends PageKeyedDataSource<Integer, Actor> {

    private static final String API_KEY = "90f9f56e299e21f06338b0197a5ff6f6" ;
    private Context mContext ;
    private static final int FIRST_PAGE = 1 ;
//    CountCallback countCallback = null ;

    private static final String TAG = "ActorDataSource";

//    public ActorDataSource(CountCallback callback) {
//        countCallback = callback ;
//    }

//    public ActorDataSource(){
//        super();

//    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull final LoadInitialCallback<Integer, Actor> callback) {
        Call<ResultsPage> call ;

        if ( QueryState.getInstance().getCurrentQuery() == null )
            call = RetrofitClient.getInstance().getService().getPopularResults(API_KEY, FIRST_PAGE) ;
        else
            call = RetrofitClient.getInstance().getService()
                    .getSearchResults(API_KEY,  QueryState.getInstance().getCurrentQuery(), FIRST_PAGE) ;

        call.enqueue(
                    new Callback<ResultsPage>() {
                        @Override
                        public void onResponse(Call<ResultsPage> call, Response<ResultsPage> response) {
                            if (response.body() != null ) {
                                callback.onResult(response.body().getResults(),
                                        null, FIRST_PAGE + 1);
                                QueryState.getInstance()
                                        .updateCount( response.body().getTotalResults() );
                                //CountNotifier.signalCount(QueryState.getInstance().getCount());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResultsPage> call, Throwable t) {
                            Toast.makeText(mContext,
                                    "Request is unsuccesful. Check internet connection.",
                                    Toast.LENGTH_LONG).show() ;
                            t.printStackTrace() ;
                        }
                    }
        ) ;
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params,
                           @NonNull final LoadCallback<Integer, Actor> callback) {
        Call<ResultsPage> call ;

        if (QueryState.getInstance().getCurrentQuery()== null)
            call = RetrofitClient.getInstance().getService().getPopularResults(API_KEY, params.key) ;
        else
            call = RetrofitClient.getInstance().getService()
                    .getSearchResults(API_KEY, QueryState.getInstance().getCurrentQuery(), params.key) ;

        call.enqueue(
                    new Callback<ResultsPage>() {
                        @Override
                        public void onResponse(Call<ResultsPage> call, Response<ResultsPage> response) {
                            Integer adjacentPageKey = (params.key > 1) ? params.key -1 : null ;

                            if (response.body() != null ) {
                                QueryState.getInstance()
                                        .updateCount( response.body().getTotalResults() );
                                callback.onResult(response.body().getResults(), adjacentPageKey);
                                //countCallback.updateResultsCount(QueryState.getInstance().getCount());
                                //CountNotifier cc = new CountNotifier(CountCallback.class);
                                //cc.signalCount(QueryState.getInstance().getCount());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResultsPage> call, Throwable t) {
                            Toast.makeText(mContext,
                                    "Request is unsuccesful. Check internet connection.",
                                    Toast.LENGTH_LONG).show() ;
                            t.printStackTrace() ;
                        }
                    }
        ) ;

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params,
                          @NonNull final LoadCallback<Integer, Actor> callback) {
        Call<ResultsPage> call ;

        if (QueryState.getInstance().getCurrentQuery() == null )
            call = RetrofitClient.getInstance().getService().getPopularResults(API_KEY, params.key) ;
        else
            call = RetrofitClient.getInstance().getService()
                    .getSearchResults(API_KEY, QueryState.getInstance().getCurrentQuery(), params.key) ;

        call.enqueue(
                    new Callback<ResultsPage>() {
                        @Override
                        public void onResponse(Call<ResultsPage> call, Response<ResultsPage> response) {

                            if (response.body() != null ) {
                                Integer key = ( params.key < response.body().getTotalPages() )?
                                                params.key + 1 : null ;

                                QueryState.getInstance()
                                        .updateCount( response.body().getTotalResults() );

                                callback.onResult(response.body().getResults(), key) ;
                            }
                        }

                        @Override
                        public void onFailure(Call<ResultsPage> call, Throwable t) {
                            Toast.makeText(mContext,
                                    "Request is unsuccesful. Check internet connection.", // may be an item ended message, not failure.
                                    Toast.LENGTH_LONG).show() ;
                            t.printStackTrace() ;
                        }
                    }
        ) ;

    }

}
