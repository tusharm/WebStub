package com.thoughtworks.webstub.dsl;

class Request {
    private String uri;
    private String method;

    Request(String method, String uri) {
        this.method = method;
        this.uri = uri;
    }

    String uri() {
        return uri;
    }

    String method() {
        return method;
    }
}
