package com.thoughtworks.test.web.dsl;

public class ResponseBuilder {
    private int status;

    public static ResponseBuilder response() {
        return new ResponseBuilder();
    }

    public ResponseBuilder withStatus(int status) {
        this.status = status;
        return this;
    }

    Response build() {
        return new Response(status);
    }
}

