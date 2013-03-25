package com.thoughtworks.webstub.config;

import org.apache.commons.lang.builder.EqualsBuilder;

public class Request {
    private String uri;
    private String method;
    private String content;

    public Request(String method, String uri) {
        this.method = method;
        this.uri = uri;
    }

    public Request(String method, String uri, String content) {
        this(method, uri);
        this.content = content;
    }

    public String content() {
        return content;
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
                .append(uri, that.uri)
                .append(content, that.content)
                .isEquals();
    }

    @Override
    public int hashCode() {
        int result = uri.hashCode();
        result = 31 * result + method.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
