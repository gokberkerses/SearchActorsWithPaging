package com.example.searchactorswithpaging.signal;

public class CountNotifier {

    private static CountCallback countCallback ;

    public CountNotifier(CountCallback cc) {
        countCallback = cc ;
    }

    public static void signalCount(int count){
        countCallback.updateResultsCount(count);
    }
}
