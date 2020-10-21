package com.zayano.testcermati.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GithubResponse {
    @SerializedName("total_count")
    @Expose
    private Integer totalCount;

    @SerializedName("incomplete_results")
    @Expose
    private Boolean incompleteResults;

    @SerializedName("items")
    @Expose
    private List<User> items = new ArrayList<>();

    public Integer getTotalCount() {
        return totalCount;
    }

    public Boolean getIncompleteResults() {
        return incompleteResults;
    }

    public List<User> getItems() {
        return items;
    }
}
