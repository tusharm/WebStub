package com.thoughtworks.webstub.config;

import org.apache.commons.lang.builder.EqualsBuilder;

public class Request {
    private String uri;
    private String method;

    public Request(String method, String uri) {
        this.method = method;
        this.uri = uri;
    }

    public String uri() {
        return uri;
    }

    public String method() {
        return method;
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof Request)) return false;

        Request that = (Request) o;
        return new EqualsBuilder()
                .append(method, that.method)
                .append(uri, that.uri).isEquals();
    }

    @Override
    public int hashCode() {
        int result = uri.hashCode();
        result = 31 * result + method.hashCode();
        return result;
    }
}
