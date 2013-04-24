package com.thoughtworks.webstub.config;

import org.apache.commons.lang.builder.EqualsBuilder;

import java.util.Collection;

public class Response {
    private int status;
    private String content;
    private Collection<Header> headers;

    public Response(int status, String content, Collection<Header> headers) {
        this.status = status;
        this.content = content;
        this.headers = headers;
    }

    public int status() {
        return status;
    }

    public String content() {
        return content;
    }

    public Collection<Header> headers() {
        return headers;
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof Response)) return false;

        Response that = (Response) o;
        return new EqualsBuilder()
                .append(status, that.status)
                .append(content, that.content).isEquals();
    }

    @Override
    public int hashCode() {
        int result = status;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
