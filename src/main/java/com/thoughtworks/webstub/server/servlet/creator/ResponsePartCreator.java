package com.thoughtworks.webstub.server.servlet.creator;

import com.thoughtworks.webstub.config.HttpConfiguration;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class ResponsePartCreator {
    protected HttpConfiguration configuration;

    protected ResponsePartCreator(HttpConfiguration  configuration) {
        this.configuration = configuration;
    }

    public abstract void createFor(HttpServletResponse response) throws IOException;
}
