package com.thoughtworks.test.web.dsl;

class Request {
    private String uri;
    private String method;

    public Request(String method, String uri) {
        this.method = method;
        this.uri = uri;
    }

    String uri() {
        return uri;
    }

    String method() {
        return method;
    }

    public void returns(Response response) {

    }
}
