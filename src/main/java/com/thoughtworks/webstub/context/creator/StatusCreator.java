package com.thoughtworks.webstub.context.creator;

import com.thoughtworks.webstub.config.HttpConfiguration;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StatusCreator extends ResponsePartCreator {
    public StatusCreator(HttpConfiguration configuration) {
        super(configuration);
    }

    @Override
    public void createFor(HttpServletResponse response) throws IOException {
        response.setStatus(configuration.response().status());
    }
}
