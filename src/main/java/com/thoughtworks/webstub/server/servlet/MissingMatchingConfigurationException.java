package com.thoughtworks.webstub.server.servlet;

import com.thoughtworks.webstub.server.servlet.matcher.RequestPartMatcher;

public class MissingMatchingConfigurationException extends Exception {
    private RequestPartMatcher matcher;

    MissingMatchingConfigurationException(RequestPartMatcher matcher) {
        this.matcher = matcher;
    }

    public RequestPartMatcher getFailedMatcher() {
        return this.matcher;
    }
}
