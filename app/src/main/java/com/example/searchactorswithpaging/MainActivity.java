package com.example.searchactorswithpaging;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.searchactorswithpaging.data.ActorViewModel;
import com.example.searchactorswithpaging.model.Actor;
import com.example.searchactorswithpaging.ui.ActorItemAdapter;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        ActorViewModel actorViewModel
                = ViewModelProviders.of(this).get(ActorViewModel.class) ;

        final ActorItemAdapter adapter = new ActorItemAdapter(this) ;

        actorViewModel.getActorPagedList()
                .observe(
                this, new Observer<PagedList<Actor>>() {
                    @Override
                    public void onChanged(@Nullable PagedList<Actor> items) {

                        //in case of any changes
                        //submitting the items to adapter
                        adapter.submitList(items);
                    }
                }
        );

        recyclerView.setAdapter(adapter);

    }
}
