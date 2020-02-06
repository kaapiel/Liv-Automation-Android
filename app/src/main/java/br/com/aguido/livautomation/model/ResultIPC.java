package br.com.aguido.livautomation.model;

import java.util.List;

/**
 * Created by vilmar on 5/15/16.
 */
public class ResultIPC {

    private static ResultIPC instance;

    public synchronized static ResultIPC get() {
        if (instance == null) {
            instance = new ResultIPC();
        }
        return instance;
    }

    private int sync = 0;

    private List<? extends Object> largeData;

    public int setLargeData(List<? extends Object> largeData) {
        this.largeData = largeData;
        return ++sync;
    }

    public List<? extends Object> getLargeData(int request) {
        return (request == sync) ? largeData : null;
    }

}
