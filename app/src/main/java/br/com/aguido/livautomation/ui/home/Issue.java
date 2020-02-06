package br.com.aguido.livautomation.ui.home;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Inmetrics on 05/09/2016.
 */
public class Issue implements Parcelable {

    private String key;
    private String summary;
    private String issueType;
    private String component;
    private String created;
    private String displayName;
    private String assignee;
    private String priority;
    private String resolution;
    private String sprint;
    private String status;

    public Issue(Parcel in) {
        key = in.readString();
        summary = in.readString();
        issueType = in.readString();
        component = in.readString();
        created = in.readString();
        displayName = in.readString();
        assignee = in.readString();
        priority = in.readString();
        resolution = in.readString();
        sprint = in.readString();
        status = in.readString();
    }

    public Issue(){

    }

    public static final Creator<Issue> CREATOR = new Creator<Issue>() {
        @Override
        public Issue createFromParcel(Parcel in) {
            return new Issue(in);
        }

        @Override
        public Issue[] newArray(int size) {
            return new Issue[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getSprint() {
        return sprint;
    }

    public void setSprint(String sprint) {
        this.sprint = sprint;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return key + " - " + priority + " - " + issueType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(summary);
        dest.writeString(issueType);
        dest.writeString(component);
        dest.writeString(created);
        dest.writeString(displayName);
        dest.writeString(assignee);
        dest.writeString(priority);
        dest.writeString(resolution);
        dest.writeString(sprint);
        dest.writeString(status);
    }
}
