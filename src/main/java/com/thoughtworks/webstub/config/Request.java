package com.thoughtworks.webstub.config;

import org.apache.commons.lang.builder.EqualsBuilder;

import java.util.ArrayList;
import java.util.Collection;

public class Request {
    private String uri;
    private String method;
    private String content;
    private Collection<Header> headers = new ArrayList<Header>();

    public Request(String method, String uri) {
        this.method = method;
        this.uri = uri;
    }

    public Request(String method, String uri, String content) {
        this(method, uri);
        this.content = content;
    }

    public Request(String method, String uri, String content, Collection<Header> headers) {
        this(method, uri, content);
        this.headers = headers;
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

    public Collection<Header> headers() {
        return headers;
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof Request)) return false;

        Request that = (Request) o;
        return new EqualsBuilder()
                .append(method, that.method)
                .append(uri, that.uri)
                .append(content, that.content)
                .append(headers, that.headers)
                .isEquals();
    }

    @Override
    public int hashCode() {
        int result = uri.hashCode();
        result = 31 * result + method.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (headers != null ? headers.hashCode() : 0);
        return result;
    }
}
