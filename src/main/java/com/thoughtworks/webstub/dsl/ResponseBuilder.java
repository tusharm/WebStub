package com.thoughtworks.webstub.dsl;

public class ResponseBuilder {
    public static ResponseBuilder response(int status) {
        return new ResponseBuilder(status);
    }

    private int status;
    private String content;

    private ResponseBuilder(int status) {
        this.status = status;
    }

    public ResponseBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    Response build() {
        return new Response(status, content);
    }
}

