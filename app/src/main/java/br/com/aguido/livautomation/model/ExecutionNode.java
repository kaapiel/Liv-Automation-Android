package br.com.aguido.livautomation.model;

import java.util.ArrayList;

/**
 * Created by Inmetrics on 22/09/2016.
 */
public class ExecutionNode {

    private int id;
    private int kind;
    private ArrayList<PropertyValue> pv;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public ArrayList<PropertyValue> getPv() {
        return pv;
    }

    public void setPv(ArrayList<PropertyValue> pv) {
        this.pv = pv;
    }
}
