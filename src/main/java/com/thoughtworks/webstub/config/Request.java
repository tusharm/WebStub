package com.thoughtworks.webstub.config;

public class Request {
    private String uri;
    private String method;

    public Request(String method, String uri) {
        this.method = method;
        this.uri = uri;
    }

    public String uri() {
        return uri;
    }

    public String method() {
        return method;
    }
}
