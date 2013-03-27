package com.thoughtworks.webstub.stub.matcher;

import com.thoughtworks.webstub.config.HttpConfiguration;

import javax.servlet.http.HttpServletRequest;

import static javax.servlet.http.HttpServletResponse.SC_NOT_IMPLEMENTED;

public class MethodMatcher extends ConfigurationMatcher {
    public MethodMatcher(HttpConfiguration configuration) {
        super(configuration, SC_NOT_IMPLEMENTED);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return configuration.method().equals(request.getMethod());
    }
}
