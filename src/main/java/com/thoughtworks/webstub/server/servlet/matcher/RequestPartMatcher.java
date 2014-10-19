package com.thoughtworks.webstub.server.servlet.matcher;

import com.thoughtworks.webstub.config.HttpConfiguration;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public abstract class RequestPartMatcher {
    protected HttpServletRequest request;
    private int failedResponseCode;

    protected RequestPartMatcher(HttpServletRequest request, int failedResponseCode) {
        this.request = request;
        this.failedResponseCode = failedResponseCode;
    }

    public int failedResponseCode() {
        return failedResponseCode;
    }

    public abstract boolean matches(HttpConfiguration configuration) throws IOException;
}
