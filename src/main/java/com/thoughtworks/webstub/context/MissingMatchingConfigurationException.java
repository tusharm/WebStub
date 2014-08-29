package com.thoughtworks.webstub.context;

import com.thoughtworks.webstub.context.matcher.RequestPartMatcher;

public class MissingMatchingConfigurationException extends Exception {
    private RequestPartMatcher matcher;

    MissingMatchingConfigurationException(RequestPartMatcher matcher) {
        this.matcher = matcher;
    }

    public RequestPartMatcher getFailedMatcher() {
        return this.matcher;
    }
}
