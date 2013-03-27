package com.thoughtworks.webstub.stub.matcher;


import com.thoughtworks.webstub.config.HttpConfiguration;

import javax.servlet.http.HttpServletRequest;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class UriMatcher extends ConfigurationMatcher {
    public UriMatcher(HttpConfiguration configuration) {
        super(configuration, SC_NOT_FOUND);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return configuration.uri().equals(request.getServletPath());
    }
}
