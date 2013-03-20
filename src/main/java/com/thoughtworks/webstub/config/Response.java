package com.thoughtworks.webstub.config;

public class Response {
    private int status;
    private String content;

    public Response(int status, String content) {
        this.status = status;
        this.content = content;
    }

    public int status() {
        return status;
    }

    public String content() {
        return content;
    }
}
