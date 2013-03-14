package com.thoughtworks.test.web.dsl;

class Response {
    private int status;

    Response(int status) {
        this.status = status;
    }

    int status() {
        return status;
    }
}
