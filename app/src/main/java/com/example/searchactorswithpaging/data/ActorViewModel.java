package com.example.searchactorswithpaging.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

import com.example.searchactorswithpaging.model.Actor;

public class ActorViewModel extends ViewModel {

    LiveData<PagedList<Actor>> actorPagedList ;
    LiveData<PageKeyedDataSource<Integer, Actor>> liveDataSource ;

    public ActorViewModel() {
        ActorDataSourceFactory actorDataSourceFactory = new ActorDataSourceFactory() ;

        liveDataSource = actorDataSourceFactory.getActorLiveDataSource() ;

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(20).build(); // by default, themoviedbapi gives 20 results by page

        actorPagedList = (new LivePagedListBuilder(actorDataSourceFactory, pagedListConfig ) )
                .build() ;
    }


    public LiveData<PagedList<Actor>> getActorPagedList () {
        return actorPagedList ;
    }
}
