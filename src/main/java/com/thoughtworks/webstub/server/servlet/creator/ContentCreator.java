package com.thoughtworks.webstub.server.servlet.creator;

import com.thoughtworks.webstub.config.HttpConfiguration;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ContentCreator extends ResponsePartCreator {
    public ContentCreator(HttpConfiguration configuration) {
        super(configuration);
    }

    @Override
    public void createFor(HttpServletResponse response) throws IOException {
        String content = responseContent();
        if (content != null)
            response.getWriter().print(content);
    }

    private String responseContent() {
        return configuration.response().content();
    }
}
