package com.thoughtworks.webstub.dsl;

class Response {
    private int status;

    Response(int status) {
        this.status = status;
    }

    int status() {
        return status;
    }
}
