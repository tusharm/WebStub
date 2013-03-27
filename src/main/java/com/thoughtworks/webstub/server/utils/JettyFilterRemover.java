package com.thoughtworks.webstub.server.utils;

import com.thoughtworks.webstub.utils.Predicate;
import com.thoughtworks.webstub.utils.PredicatedPartition;
import org.eclipse.jetty.servlet.*;

import java.util.List;

import static com.thoughtworks.webstub.utils.ListUtils.partition;
import static java.util.Arrays.asList;
import static org.apache.commons.lang.ArrayUtils.contains;

public class JettyFilterRemover extends JettyHandlerRemover<FilterMapping, FilterHolder> {
    public JettyFilterRemover(org.eclipse.jetty.servlet.ServletContextHandler contextHandler) {
        super(contextHandler);
    }

    @Override
    PredicatedPartition<FilterMapping> partitionMappingsBy(final String pathSpec) {
        return partition(asList(servletHandler().getFilterMappings()), new Predicate<FilterMapping>() {
            @Override
            public boolean satisfies(FilterMapping mapping) {
                return contains(mapping.getPathSpecs(), pathSpec);
            }
        });
    }

    @Override
    void setNewMappings(List<FilterMapping> mappings) {
        servletHandler().setFilterMappings(mappings.toArray(new FilterMapping[]{}));
    }

    @Override
    PredicatedPartition<FilterHolder> partitionHoldersBy(final String pathSpec) {
        return partition(asList(servletHandler().getFilters()), new Predicate<FilterHolder>() {
            @Override
            public boolean satisfies(FilterHolder holder) {
                return holder.getName().equals(pathSpec);
            }
        });
    }

    @Override
    void setNewHolders(List<FilterHolder> holders) {
        servletHandler().setFilters(holders.toArray(new FilterHolder[]{}));
    }

    @Override
    String getHandlerName(FilterMapping mapping) {
        return mapping.getFilterName();
    }
}
