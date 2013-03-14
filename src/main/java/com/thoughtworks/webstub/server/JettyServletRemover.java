package com.thoughtworks.webstub.server;

import com.thoughtworks.webstub.utils.Partition;
import com.thoughtworks.webstub.utils.Predicate;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletMapping;

import static com.thoughtworks.webstub.utils.ListUtils.split;
import static java.util.Arrays.asList;
import static org.apache.commons.lang.ArrayUtils.contains;

class JettyServletRemover {
    private ServletHandler servletHandler;

    JettyServletRemover(ServletContextHandler contextHandler) {
        servletHandler = contextHandler.getServletHandler();
    }

    void remove(final String servletPath) {
        Partition<ServletMapping> mappings = partitionMappingsBy(servletPath);
        if (mappings.left().isEmpty())
            return;

        Partition<ServletHolder> holders = partitionHoldersBy(mappings.left().get(0).getServletName());

        servletHandler.setServlets(holders.right().toArray(new ServletHolder[]{}));
        servletHandler.setServletMappings(mappings.right().toArray(new ServletMapping[]{}));
    }

    private Partition<ServletHolder> partitionHoldersBy(final String servletName) {
        return split(asList(servletHandler.getServlets()), new Predicate<ServletHolder>() {
            @Override
            public boolean satisfies(ServletHolder holder) {
                return holder.getName().equals(servletName);
            }
        });
    }

    private Partition<ServletMapping> partitionMappingsBy(final String servletPath) {
        return split(asList(servletHandler.getServletMappings()), new Predicate<ServletMapping>() {
            @Override
            public boolean satisfies(ServletMapping mapping) {
                return contains(mapping.getPathSpecs(), servletPath);
            }
        });
    }

}
