package com.nezspencer.githubprofiles.api;

public class UserResponse {
    private int total_count;
    private boolean incomplete_results;
    private UserResponseItems[] items;

    public int getTotal_count() {
        return this.total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public boolean getIncomplete_results() {
        return this.incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public UserResponseItems[] getItems() {
        return this.items;
    }

    public void setItems(UserResponseItems[] items) {
        this.items = items;
    }
}
