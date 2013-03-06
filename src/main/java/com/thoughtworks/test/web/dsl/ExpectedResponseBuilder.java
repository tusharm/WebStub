package com.thoughtworks.test.web.dsl;

public class ExpectedResponseBuilder {
    private int statusCode = 200;

    public static ExpectedResponseBuilder response() {
        return new ExpectedResponseBuilder();
    }

    public ExpectedResponseBuilder withStatus(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public ExpectedResponse build() {
        return new ExpectedResponse(statusCode);
    }
}
