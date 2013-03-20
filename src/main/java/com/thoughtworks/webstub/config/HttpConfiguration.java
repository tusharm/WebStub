package com.thoughtworks.webstub.config;


import org.apache.commons.lang.builder.EqualsBuilder;

import static java.lang.String.format;

public class HttpConfiguration {
    private final String method;
    private final String uri;
    private final int status;
    private String content;

    public HttpConfiguration(String method, String uri, int status) {
        this.method = method;
        this.uri = uri;
        this.status = status;
    }

    public HttpConfiguration(String method, String uri, int status, String content) {
        this(method, uri, status);
        this.content = content;
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

    public String content() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof HttpConfiguration)) return false;

        HttpConfiguration that = (HttpConfiguration) o;
        return new EqualsBuilder()
                .append(status, that.status)
                .append(method, that.method)
                .append(uri, that.uri)
                .append(content, that.content)
                .isEquals();
    }

    @Override
    public int hashCode() {
        int result = method.hashCode();
        result = 31 * result + uri.hashCode();
        result = 31 * result + status;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return format("HttpConfiguration {" +
                "method:%s, " +
                "uri:%s, " +
                "status:%d, " +
                "content:%s" +
                "}", method, uri, status, content);
    }
}

