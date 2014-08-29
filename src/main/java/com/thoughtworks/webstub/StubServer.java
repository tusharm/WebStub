package com.thoughtworks.webstub;

import com.thoughtworks.webstub.dsl.StubDsl;
import com.thoughtworks.webstub.server.HttpServer;
import com.thoughtworks.webstub.server.JettyHttpServer;
import com.thoughtworks.webstub.context.ConfigurableContext;

public class StubServer {
    public static StubServer newServer(int port) {
        return new StubServer(new JettyHttpServer(port));
    }

    private HttpServer httpServer;

    private StubServer(HttpServer httpServer) {
        this.httpServer = httpServer;
    }

    public void start() {
        httpServer.start();
    }

    public void stop() {
        httpServer.stop();
    }

    public StubDsl stub(String contextRoot) {
        return new StubDsl(new ConfigurableContext(httpServer, contextRoot));
    }
}