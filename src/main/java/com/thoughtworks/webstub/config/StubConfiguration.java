package com.thoughtworks.webstub.config;

import org.apache.commons.lang.builder.EqualsBuilder;

public class StubConfiguration {
    private final String method;
    private final String uri;
    private final int status;

    public StubConfiguration(String method, String uri, int status) {
        this.method = method;
        this.uri = uri;
        this.status = status;
    }

    public String method() {
        return method;
    }

    public String uri() {
        return uri;
    }

    public int status() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StubConfiguration)) return false;

        StubConfiguration that = (StubConfiguration) o;
        return new EqualsBuilder()
                .append(status, that.status)
                .append(method, that.method)
                .append(uri, that.uri)
                .isEquals();
    }

    @Override
    public int hashCode() {
        int result = method.hashCode();
        result = 31 * result + uri.hashCode();
        result = 31 * result + status;
        return result;
    }
}

