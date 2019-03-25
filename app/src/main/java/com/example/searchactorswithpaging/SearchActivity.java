package com.example.searchactorswithpaging;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.searchactorswithpaging.data.ActorViewModel;
import com.example.searchactorswithpaging.data.QueryState;
import com.example.searchactorswithpaging.model.Actor;
import com.example.searchactorswithpaging.signal.CountCallback;
import com.example.searchactorswithpaging.signal.CountNotifier;
import com.example.searchactorswithpaging.ui.ActorItemAdapter;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;


public class SearchActivity extends AppCompatActivity implements CountCallback {
    private static final String TAG = "SearchActivity";
    Context mContext ;
    private CountNotifier countNotifier = new CountNotifier(this) ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: " );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent: " );
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        Log.d(TAG, "handleIntent: " );
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    private void doSearch(final String query){
        Log.d(TAG, "doSearch: ");
        QueryState.getInstance().updateQuery(query);

        RecyclerView recyclerView ;
        recyclerView = findViewById(R.id.recyclerviewS);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        Log.d(TAG, "doSearch: 1: ");

        ActorViewModel actorViewModel
                = ViewModelProviders.of(this).get(ActorViewModel.class) ;

        actorViewModel.refresh() ;
        final ActorItemAdapter adapter = new ActorItemAdapter(this) ;


        actorViewModel.getActorPagedList()
                .observe(
                        this, new Observer<PagedList<Actor>>() {
                            @Override
                            public void onChanged(@Nullable PagedList<Actor> actors) {
                                adapter.submitList(actors);

                                //TextView resultsCountField = findViewById(R.id.results_number);
                                //resultsCountField.setText(getResources()
                                //        .getString(R.string.result, QueryState.getInstance().getCount())) ;

                            }
                        }
                );

        recyclerView.setAdapter(adapter);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_item, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo( searchManager.getSearchableInfo(getComponentName()) );

        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        //QueryState.getInstance().resetQuery() ;
    }

    @Override
    public void updateResultsCount (int count){
          TextView resultsCountField = findViewById(R.id.results_number);
          resultsCountField.setText(
                  getResources().getString(R.string.result, count) ) ;
    }

}
