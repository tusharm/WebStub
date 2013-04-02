package com.thoughtworks.webstub.stub;

import com.thoughtworks.webstub.stub.matcher.RequestPartMatcher;

class MissingMatchingConfigurationException extends Exception {
    private RequestPartMatcher matcher;

    MissingMatchingConfigurationException(RequestPartMatcher matcher) {
        this.matcher = matcher;
    }

    RequestPartMatcher getFailedMatcher() {
        return this.matcher;
    }
}
