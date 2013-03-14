package com.thoughtworks.test.web;

import com.thoughtworks.test.web.server.JettyHttpServer;
import com.thoughtworks.test.web.stub.HttpServerStub;
import com.thoughtworks.test.web.stub.HttpServer;

public class ServerStubFactory {
    public static HttpServerStub dslServer(int port, String contextRoot) {
        HttpServer httpServer = new JettyHttpServer(port, contextRoot);
        return new HttpServerStub(httpServer);
    }
}
