package com.thoughtworks.webstub.stub.creator;

import com.thoughtworks.webstub.config.HttpConfiguration;
import com.thoughtworks.webstub.config.Header;

import javax.servlet.http.HttpServletResponse;

public class HeadersCreator extends ResponsePartCreator {
    public HeadersCreator(HttpConfiguration configuration) {
        super(configuration);
    }

    @Override
    public void createFor(HttpServletResponse response) {
        for (Header header : configuration.response().headers()) {
            response.setHeader(header.name(), header.value());
        }
    }
}
