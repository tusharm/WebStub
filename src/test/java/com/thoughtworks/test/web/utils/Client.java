package com.thoughtworks.test.web.utils;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import java.io.IOException;

public class Client {
    private DefaultHttpClient httpClient;

    public Client() {
        httpClient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 1000);
    }

    public Response get(String url) {
        HttpGet httpget = new HttpGet(url);
        try {
            return new Response(httpClient.execute(httpget));
        } catch (IOException e) {
            throw new RuntimeException("Error executing GET request", e);
        }
        finally {
            httpget.releaseConnection();
        }
    }
}
