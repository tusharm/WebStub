package com.thoughtworks.webstub.stub.matcher;

import com.thoughtworks.webstub.config.HttpConfiguration;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static java.net.URLDecoder.decode;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static org.apache.commons.lang.StringUtils.isBlank;

public class QueryStringMatcher extends RequestPartMatcher {
    public QueryStringMatcher(HttpServletRequest request) {
        super(request, SC_BAD_REQUEST);
    }

    @Override
    public boolean matches(HttpConfiguration configuration) throws IOException {
        String queryString = queryString(configuration);
        return isBlank(queryString) || queryString.equals(queryString(request));
    }

    private String queryString(HttpServletRequest request) throws UnsupportedEncodingException {
        String queryString = request.getQueryString();
        return (queryString == null) ? null : decode(queryString, "UTF-8");
    }

    private String queryString(HttpConfiguration configuration) {
        String[] splitted = configuration.request().uri().split("\\?");
        return (splitted.length == 1) ? "" : splitted[1];
    }
}
