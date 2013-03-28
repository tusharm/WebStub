package com.thoughtworks.webstub.utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static com.thoughtworks.webstub.utils.CollectionUtils.map;
import static java.util.Arrays.asList;

public class Response {
    private HttpResponse response;
    private String cachedContent;

    public Response(HttpResponse response) {
        this.response = response;
    }

    public Integer status() {
        return response.getStatusLine().getStatusCode();
    }

    public Collection<String> header(String name) {
        return map(asList(response.getHeaders(name)), new Mapper<Header, String>() {
            @Override
            public String map(Header header) {
                return header.getValue();
            }
        });
    }

    public String content() {
        if (cachedContent != null)
            return cachedContent;

        HttpEntity entity = response.getEntity();
        if (entity == null) {
            throw new RuntimeException("Response does not contain entity");
        }

        try {
            cachedContent = EntityUtils.toString(entity);
            return cachedContent;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                EntityUtils.consume(entity);
            } catch (IOException e) {
                throw new RuntimeException("Error closing entity stream", e);
            }
        }
    }
}
