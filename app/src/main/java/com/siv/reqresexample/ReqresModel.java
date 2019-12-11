package com.siv.reqresexample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReqresModel {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("first_name")
    @Expose
    public String first_name;

    @SerializedName("last_name")
    @Expose
    public String last_name;

    @SerializedName("avatar")
    @Expose
    public String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
