package com.thoughtworks.webstub.config;


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Objects;

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
    public int hashCode() {
        int result = request.hashCode();
        result = 31 * result + response.hashCode();
        return result;
    }

    @Override

    public String toString() {
        return new ToStringBuilder(this)
                .append("method", request.method())
                .append("uri", request.uri())
                .append("request content", request.content())
                .append("response status", response.status())
                .append("response content", response.content())
                .toString();
    }
}

