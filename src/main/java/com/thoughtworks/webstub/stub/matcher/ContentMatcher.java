package com.thoughtworks.webstub.stub.matcher;

import com.thoughtworks.webstub.config.HttpConfiguration;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static org.apache.commons.lang.StringUtils.isBlank;

public class ContentMatcher extends RequestPartMatcher {
    public ContentMatcher(HttpServletRequest request) {
        super(request, SC_BAD_REQUEST);
    }

    @Override
    public boolean matches(HttpConfiguration configuration) throws IOException {
        String configuredContent = configuration.request().content();
        return isBlank(configuredContent) || configuredContent.equals(getContent(request));
    }

    private String getContent(HttpServletRequest req) throws IOException {
        BufferedReader reader = req.getReader();
        try {
            StringBuilder builder = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } finally {
            reader.close();
        }
    }
}
