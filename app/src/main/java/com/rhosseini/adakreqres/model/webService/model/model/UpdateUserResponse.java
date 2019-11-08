package com.rhosseini.adakreqres.model.webService.model.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateUserResponse {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("job")
    @Expose
    private String job;

    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public UpdateUserResponse(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}