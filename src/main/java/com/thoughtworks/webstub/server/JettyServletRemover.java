package com.thoughtworks.webstub.server;

import com.thoughtworks.webstub.utils.PredicatedPartition;
import com.thoughtworks.webstub.utils.Predicate;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletMapping;

import static com.thoughtworks.webstub.utils.ListUtils.partition;
import static java.util.Arrays.asList;
import static org.apache.commons.lang.ArrayUtils.contains;

class JettyServletRemover {
    private ServletHandler servletHandler;

    JettyServletRemover(ServletContextHandler contextHandler) {
        servletHandler = contextHandler.getServletHandler();
    }

    void remove(final String servletPath) {
        PredicatedPartition<ServletMapping> mappings = partitionMappingsBy(servletPath);
        if (mappings.satisfying().isEmpty())
            return;

        PredicatedPartition<ServletHolder> holders = partitionHoldersBy(mappings.satisfying().get(0).getServletName());

        servletHandler.setServlets(holders.notSatisfying().toArray(new ServletHolder[]{}));
        servletHandler.setServletMappings(mappings.notSatisfying().toArray(new ServletMapping[]{}));
    }

    private PredicatedPartition<ServletHolder> partitionHoldersBy(final String servletName) {
        return partition(asList(servletHandler.getServlets()), new Predicate<ServletHolder>() {
            @Override
            public boolean satisfies(ServletHolder holder) {
                return holder.getName().equals(servletName);
            }
        });
    }

    private PredicatedPartition<ServletMapping> partitionMappingsBy(final String servletPath) {
        return partition(asList(servletHandler.getServletMappings()), new Predicate<ServletMapping>() {
            @Override
            public boolean satisfies(ServletMapping mapping) {
                return contains(mapping.getPathSpecs(), servletPath);
            }
        });
    }

}
