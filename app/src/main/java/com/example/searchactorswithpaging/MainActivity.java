package com.example.searchactorswithpaging;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;

import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.example.searchactorswithpaging.data.ActorViewModel;
import com.example.searchactorswithpaging.model.Actor;
import com.example.searchactorswithpaging.ui.ActorItemAdapter;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerviewM);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        ActorViewModel actorViewModel
                = ViewModelProviders.of(this).get(ActorViewModel.class) ;

        final ActorItemAdapter adapter = new ActorItemAdapter(this) ;

        actorViewModel.getActorPagedList()
                .observe(
                this, new Observer<PagedList<Actor>>() {
                    @Override
                    public void onChanged(@Nullable PagedList<Actor> actors) {

                        adapter.submitList(actors);
                    }
                }
        );

        recyclerView.setAdapter(adapter);

        // from where searchactivity be called?
        //        Intent intent = getIntent();
        //        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
        //            String query = intent.getStringExtra(SearchManager.QUERY);
        //            Log.v("QUERY", query);
        //        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_item, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo( searchManager.getSearchableInfo(getComponentName()) );

        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        if (id == R.id.action_search) {
//            //this is the method that displays the search dialog
//            onSearchRequested();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
