package com.thoughtworks.webstub.server.utils;

import com.thoughtworks.webstub.utils.Mapper;
import com.thoughtworks.webstub.utils.PredicatedPartition;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;

import java.util.Collection;

import static com.thoughtworks.webstub.utils.CollectionUtils.map;

public abstract class JettyHandlerRemover<Mapping, Holder> {
    private ServletHandler servletHandler;

    protected JettyHandlerRemover(ServletContextHandler contextHandler) {
        servletHandler = contextHandler.getServletHandler();
    }

    public void remove(String pathSpec) {
        PredicatedPartition<Mapping> mappings = partitionMappingsBy(pathSpec);
        if (mappings.satisfying().isEmpty())
            return;

        Collection<String> handlerNames = handlerNames(mappings.satisfying());
        PredicatedPartition<Holder> holders = partitionHoldersBy(handlerNames);

        setNewHolders(holders.notSatisfying());
        setNewMappings(mappings.notSatisfying());
    }

    private Collection<String> handlerNames(Collection<Mapping> mappings) {
        return map(mappings, new Mapper<Mapping, String>() {
            @Override
            public String map(Mapping mapping) {
                return getHandlerName(mapping);
            }
        });
    }

    protected ServletHandler servletHandler() {
        return servletHandler;
    }

    abstract PredicatedPartition<Mapping> partitionMappingsBy(String pathSpec);

    abstract void setNewMappings(Collection<Mapping> mappings);

    abstract PredicatedPartition<Holder> partitionHoldersBy(Collection<String> handlerNames);

    abstract void setNewHolders(Collection<Holder> holders);

    abstract String getHandlerName(Mapping mapping);
}
