package com.thoughtworks.webstub.dsl.builders;

import com.thoughtworks.webstub.config.Response;
import com.thoughtworks.webstub.config.Header;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ResponseBuilder {
    public static ResponseBuilder response(int status) {
        return new ResponseBuilder(status);
    }

    private int status;
    private String content;
    private Collection<Header> headers = new ArrayList<Header>();

    private ResponseBuilder(int status) {
        this.status = status;
    }

    public ResponseBuilder withContent(String content) {
        return withContent(new StringContentBuilder(content));
    }

    public ResponseBuilder withContent(ContentBuilder contentBuilder) {
        this.content = contentBuilder.build();
        return this;
    }

    public ResponseBuilder withHeader(String name, String value) {
        headers.add(new Header(name, value));
        return this;
    }

    public ResponseBuilder withHeaders(Map<String, String> headersMap) {
        headers.clear();
        for (Map.Entry<String, String> entry : headersMap.entrySet()) {
            headers.add(new Header(entry.getKey(), entry.getValue()));
        }
        return this;
    }

    public Response build() {
        return new Response(status, content, headers);
    }
}

