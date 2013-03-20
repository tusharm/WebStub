package com.thoughtworks.webstub.config;

import org.apache.commons.lang.builder.EqualsBuilder;

public class Response {
    private int status;
    private String content;

    public Response(int status, String content) {
        this.status = status;
        this.content = content;
    }

    public int status() {
        return status;
    }

    public String content() {
        return content;
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
