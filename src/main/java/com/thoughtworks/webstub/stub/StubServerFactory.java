package com.thoughtworks.webstub.stub;

import com.thoughtworks.webstub.server.HttpServer;
import com.thoughtworks.webstub.server.JettyHttpServer;
import com.thoughtworks.webstub.server.ServletContextHandler;

import static com.thoughtworks.webstub.server.ServletContextFactory.create;

public class StubServerFactory {
    public static HttpServerStub stubServer(int port, String contextRoot) {
        HttpServer httpServer = new JettyHttpServer(port);
        ServletContextHandler contextHandler = create(contextRoot);
        httpServer.addContext(contextHandler);
        return new HttpServerStub(httpServer, contextHandler);
    }
}
