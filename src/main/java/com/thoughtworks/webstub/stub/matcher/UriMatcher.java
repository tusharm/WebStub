package com.thoughtworks.webstub.stub.matcher;


import com.thoughtworks.webstub.config.HttpConfiguration;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class UriMatcher extends RequestPartMatcher {
    public UriMatcher(HttpServletRequest request) {
        super(request, SC_NOT_FOUND);
    }

    @Override
    public boolean matches(HttpConfiguration configuration) throws IOException {
        return getUri(configuration).equals(request.getServletPath());
    }

    private String getUri(HttpConfiguration configuration) {
        return configuration.request().uri().split("\\?")[0];
    }
}
