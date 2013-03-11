package com.thoughtworks.test.web;

import com.thoughtworks.test.web.server.ConfigurableServer;

public class HttpServerStub extends ConfigurableServer {

    public HttpServerStub(int port, String contextRoot) {
        super(port, contextRoot);
    }

    public void reset() {
    }
}

