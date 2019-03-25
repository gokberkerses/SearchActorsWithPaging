package com.example.searchactorswithpaging.data;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.example.searchactorswithpaging.model.Actor;

public class ActorDataSourceFactory extends DataSource.Factory{

    private MutableLiveData < PageKeyedDataSource<Integer, Actor> > actorLiveDataSource ;

    @Override
    public DataSource<Integer, Actor> create(){
        ActorDataSource actorDataSource = new ActorDataSource() ;

        actorLiveDataSource = MutableData.getInstance().getLiveDataByQueryState();
        actorLiveDataSource.postValue(actorDataSource) ;

        return actorDataSource ;
    }

    MutableLiveData < PageKeyedDataSource<Integer, Actor> >
        getActorLiveDataSource() {

        actorLiveDataSource = MutableData.getInstance().getLiveDataByQueryState();
        return actorLiveDataSource ;
    }

}
