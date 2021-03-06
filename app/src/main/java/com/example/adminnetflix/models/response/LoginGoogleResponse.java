package com.example.adminnetflix.models.response;

import java.io.Serializable;

public class LoginGoogleResponse implements Serializable {
    private int status;
    private boolean success;
    private String accesstoken;
    private String msg;
    private UserGoogle user;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserGoogle getUser() {
        return user;
    }

    public void setUser(UserGoogle user) {
        this.user = user;
    }
}
