package com.example.searchactorswithpaging.data;

public class QueryState {
    private static final QueryState ourInstance = new QueryState();

    private String currentQuery ;
    private int    resultsCount ;

    public static QueryState getInstance() {
        return ourInstance;
    }

    private QueryState() {
        currentQuery = null ;
        resultsCount = 0 ;
    }

    public String getCurrentQuery() {
        return currentQuery ;
    }

    public int getCount() {
        return resultsCount ;
    }

    public void updateQuery(String query) {
        currentQuery = query ;
    }

    public void updateCount(int count){
        resultsCount = count ;
    }

    public void resetQuery(){
        currentQuery = null ;
        //resultsCount = 0 ;

    }
}
