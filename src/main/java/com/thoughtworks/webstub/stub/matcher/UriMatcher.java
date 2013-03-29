package com.thoughtworks.webstub.stub.matcher;


import com.thoughtworks.webstub.config.HttpConfiguration;

import javax.servlet.http.HttpServletRequest;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class UriMatcher extends RequestPartMatcher {
    public UriMatcher(HttpConfiguration configuration) {
        super(configuration, SC_NOT_FOUND);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return requestUri().equals(request.getServletPath());
    }

    private String requestUri() {
        return configuration.request().uri();
    }
}
