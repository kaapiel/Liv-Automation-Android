package br.com.pontomobi.livelopontos.model;

import java.util.ArrayList;

/**
 * Created by Inmetrics on 22/09/2016.
 */
public class PropertyValue {

    private ArrayList<String> children = new ArrayList<>();
    private int modifyCount;
    private String name;
    private String nodeID;
    private String propertyID;
    private String propertyTypeID;
    private int type;
    private String value;

    public ArrayList<String> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<String> children) {
        this.children = children;
    }

    public int getModifyCount() {
        return modifyCount;
    }

    public void setModifyCount(int modifyCount) {
        this.modifyCount = modifyCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodeID() {
        return nodeID;
    }

    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }

    public String getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
    }

    public String getPropertyTypeID() {
        return propertyTypeID;
    }

    public void setPropertyTypeID(String propertyTypeID) {
        this.propertyTypeID = propertyTypeID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
