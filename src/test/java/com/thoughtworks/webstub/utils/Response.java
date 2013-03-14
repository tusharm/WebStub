package com.thoughtworks.webstub.utils;

import org.apache.http.HttpResponse;

public class Response {
    private HttpResponse response;

    public Response(HttpResponse response) {
        this.response = response;
    }

    public Integer status() {
        return response.getStatusLine().getStatusCode();
    }
}
