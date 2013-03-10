package com.thoughtworks.test.web.server;

import com.thoughtworks.test.utils.Partition;
import org.apache.commons.collections.Predicate;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletMapping;

import static com.thoughtworks.test.utils.ListUtils.split;
import static java.util.Arrays.asList;

class JettyServletRemover {
    private ServletHandler servletHandler;

    JettyServletRemover(ServletContextHandler contextHandler) {
        servletHandler = contextHandler.getServletHandler();
    }

    void remove(final String servletPath) {
        final Partition<ServletMapping> mappings = split(asList(servletHandler.getServletMappings()), new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                ServletMapping mapping = (ServletMapping) o;
                return asList(mapping.getPathSpecs()).contains(servletPath);
            }
        });

        if (!mappings.left().isEmpty()) {
            Partition<ServletHolder> holders = split(asList(servletHandler.getServlets()), new Predicate() {
                @Override
                public boolean evaluate(Object o) {
                    ServletHolder holder = (ServletHolder) o;
                    return holder.getName().equals(mappings.left().get(0).getServletName());
                }
            });

            servletHandler.setServlets(holders.right().toArray(new ServletHolder[]{}));
            servletHandler.setServletMappings(mappings.right().toArray(new ServletMapping[]{}));
        }
    }

}
