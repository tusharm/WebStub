package com.thoughtworks.webstub.stub.matcher;

import com.thoughtworks.webstub.config.HttpConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.io.IOUtils.readLines;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.join;

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
        ServletInputStream inputStream = null;
        try {
            inputStream = req.getInputStream();
            List<String> lines = readLines(inputStream);
            return join(lines, "\n");
        } finally {
            if (inputStream != null)
                inputStream.close();
        }
    }
}
