package com.thoughtworks.webstub.stub.matcher;

import com.thoughtworks.webstub.config.HttpConfiguration;

import javax.servlet.http.HttpServletRequest;

import static javax.servlet.http.HttpServletResponse.SC_NOT_IMPLEMENTED;

public class MethodMatcher extends RequestPartMatcher {
    public MethodMatcher(HttpConfiguration configuration) {
        super(configuration, SC_NOT_IMPLEMENTED);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return requestMethod().equals(request.getMethod());
    }

    private String requestMethod() {
        return configuration.request().method();
    }
}
