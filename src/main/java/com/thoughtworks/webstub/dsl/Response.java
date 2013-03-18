package com.thoughtworks.webstub.dsl;

class Response {
    private int status;
    private String content;

    Response(int status, String content) {
        this.status = status;
        this.content = content;
    }

    int status() {
        return status;
    }

    String content() {
        return content;
    }
}
