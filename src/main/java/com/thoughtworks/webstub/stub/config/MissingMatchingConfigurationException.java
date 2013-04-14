package com.thoughtworks.webstub.stub.config;

import com.thoughtworks.webstub.stub.matcher.RequestPartMatcher;

public class MissingMatchingConfigurationException extends Exception {
    private RequestPartMatcher matcher;

    MissingMatchingConfigurationException(RequestPartMatcher matcher) {
        this.matcher = matcher;
    }

    public RequestPartMatcher getFailedMatcher() {
        return this.matcher;
    }
}
