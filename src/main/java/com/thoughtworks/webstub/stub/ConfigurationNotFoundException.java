package com.thoughtworks.webstub.stub;

import com.thoughtworks.webstub.stub.matcher.RequestPartMatcher;

class ConfigurationNotFoundException extends Exception {
    private RequestPartMatcher matcher;

    ConfigurationNotFoundException(RequestPartMatcher matcher) {
        this.matcher = matcher;
    }

    RequestPartMatcher getFailedMatcher() {
        return this.matcher;
    }
}
