package com.example.searchactorswithpaging.data;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;

import com.example.searchactorswithpaging.model.Actor;

public class MutableData {
    private static final MutableData ourInstance = new MutableData();

    MutableLiveData< PageKeyedDataSource<Integer, Actor> > popularData ;

    MutableLiveData< PageKeyedDataSource<Integer, Actor> > searchData;

    public static MutableData getInstance() {
        return ourInstance;
    }

    private MutableData() {
        popularData = new MutableLiveData<>() ;
        searchData  = new MutableLiveData<>() ;
    }

    public MutableLiveData< PageKeyedDataSource<Integer, Actor> >
    getLiveDataByQueryState(){
        MutableLiveData< PageKeyedDataSource<Integer, Actor> > currentData ;

        if ( QueryState.getInstance().getCurrentQuery() == null ) {
            currentData = popularData ;
        }
        else {
            currentData = searchData ;
        }

        return currentData ;
    }

    public void onSuccessiveSearch(){
        searchData = new MutableLiveData<>() ;
    }

}
