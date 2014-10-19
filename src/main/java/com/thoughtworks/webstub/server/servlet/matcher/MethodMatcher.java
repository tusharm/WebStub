package com.thoughtworks.webstub.server.servlet.matcher;

import com.thoughtworks.webstub.config.HttpConfiguration;

import javax.servlet.http.HttpServletRequest;

import static javax.servlet.http.HttpServletResponse.SC_NOT_IMPLEMENTED;

public class MethodMatcher extends RequestPartMatcher {
    public MethodMatcher(HttpServletRequest request) {
        super(request, SC_NOT_IMPLEMENTED);
    }

    @Override
    public boolean matches(HttpConfiguration configuration) {
        return getMethod(configuration).equals(request.getMethod());
    }

    private String getMethod(HttpConfiguration configuration) {
        return configuration.request().method();
    }
}
