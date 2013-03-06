package com.thoughtworks.test.web.dsl;

public class ExpectedResponse {
    private int statusCode;

    ExpectedResponse(int statusCode){
        this.statusCode = statusCode;
    }
}
