package com.thoughtworks.webstub.dsl;

import com.thoughtworks.webstub.config.Response;

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
        this.content = content;
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

    Response build() {
        return new Response(status, content, headers);
    }
}

