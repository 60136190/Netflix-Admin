package com.example.adminnetflix.models.response;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    private int status;
    private boolean success;
    private String accesstoken;
    private String msg;

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

    public String getAccessToken() {
        return accesstoken;
    }

    public void setAccessToken(String accessToken) {
        this.accesstoken = accessToken;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
