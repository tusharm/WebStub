package com.thoughtworks.webstub.server.context;

import com.thoughtworks.webstub.server.HttpServer;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

public class WebConsoleContext extends ContextHandler {

    public WebConsoleContext(HttpServer httpServer, String webAppRootDir) {
        ResourceHandler handler = new ResourceHandler();
        handler.setBaseResource(Resource.newClassPathResource(webAppRootDir));
        handler.setDirectoriesListed(true);
        handler.setWelcomeFiles(new String[] { "index.html" });

        this.setHandler(handler);
        this.setContextPath("/");

        httpServer.addContext(this);
    }
}
