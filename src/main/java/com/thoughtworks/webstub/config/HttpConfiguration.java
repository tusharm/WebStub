package com.thoughtworks.webstub.config;


import org.apache.commons.lang.builder.EqualsBuilder;

import static java.lang.String.format;

public class HttpConfiguration {
    private Request request;
    private Response response;

    public HttpConfiguration(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public String method() {
        return request.method();
    }

    public String uri() {
        return request.uri();
    }

    public int status() {
        return response.status();
    }

    public String content() {
        return response.content();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof HttpConfiguration)) return false;

        HttpConfiguration that = (HttpConfiguration) o;
        return new EqualsBuilder()
                .append(request, that.request)
                .append(response, that.response)
                .isEquals();
    }

    @Override
    public String toString() {
        return format("HttpConfiguration {" +
                "method:%s, " +
                "uri:%s, " +
                "status:%d, " +
                "content:%s" +
                "}", method(), uri(), status(), content());
    }
}

