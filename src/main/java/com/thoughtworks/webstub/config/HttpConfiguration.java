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

    public Request request() {
        return request;
    }

    public Response response() {
        return response;
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
                "request content:%s, " +
                "status:%d, " +
                "response content:%s" +
                "}", request.method(), request.uri(), request.content(), response.status(), response.content());
    }
}

