package com.thoughtworks.webstub.server.utils;

import com.thoughtworks.webstub.utils.Predicate;
import com.thoughtworks.webstub.utils.PredicatedPartition;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletMapping;

import java.util.List;

import static com.thoughtworks.webstub.utils.ListUtils.partition;
import static java.util.Arrays.asList;
import static org.apache.commons.lang.ArrayUtils.contains;

public class JettyServletRemover extends JettyHandlerRemover<ServletMapping, ServletHolder> {
    public JettyServletRemover(org.eclipse.jetty.servlet.ServletContextHandler contextHandler) {
        super(contextHandler);
    }

    @Override
    PredicatedPartition<ServletHolder> partitionHoldersBy(final String pathSpec) {
        return partition(asList(servletHandler().getServlets()), new Predicate<ServletHolder>() {
            @Override
            public boolean satisfies(ServletHolder holder) {
                return holder.getName().equals(pathSpec);
            }
        });
    }

    @Override
    PredicatedPartition<ServletMapping> partitionMappingsBy(final String pathSpec) {
        return partition(asList(servletHandler().getServletMappings()), new Predicate<ServletMapping>() {
            @Override
            public boolean satisfies(ServletMapping mapping) {
                return contains(mapping.getPathSpecs(), pathSpec);
            }
        });
    }

    @Override
    String getHandlerName(ServletMapping servletMapping) {
        return servletMapping.getServletName();
    }

    @Override
    void setNewMappings(List<ServletMapping> mappings) {
        servletHandler().setServletMappings(mappings.toArray(new ServletMapping[]{}));
    }

    @Override
    void setNewHolders(List<ServletHolder> holders) {
        servletHandler().setServlets(holders.toArray(new ServletHolder[]{}));
    }

}
