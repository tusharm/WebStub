package com.thoughtworks.test.web.dsl;

public class Response {
    private int status;

    public Response(int status) {
        this.status = status;
    }

    int status() {
        return status;
    }
}
