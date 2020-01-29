package com.softserve.academy.model;

public class Error {
    private int status;
    private String url;
    private String message;

    public Error(int status, String url, String message) {
        this.status = status;
        this.url = url;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}