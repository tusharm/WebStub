package com.thoughtworks.webstub.server.utils;

import com.thoughtworks.webstub.utils.PredicatedPartition;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;

import java.util.List;

import static org.apache.commons.collections.CollectionUtils.collect;
import static org.apache.commons.lang.ArrayUtils.contains;

public abstract class JettyHandlerRemover<Mapping, Holder> {
    private ServletHandler servletHandler;

    protected JettyHandlerRemover(ServletContextHandler contextHandler) {
        servletHandler = contextHandler.getServletHandler();
    }

    public void remove(String pathSpec) {
        PredicatedPartition<Mapping> mappings = partitionMappingsBy(pathSpec);
        if (mappings.satisfying().isEmpty())
            return;

        List<String> handlerNames = handlerNames(mappings.satisfying());
        PredicatedPartition<Holder> holders = partitionHoldersBy(handlerNames);

        setNewHolders(holders.notSatisfying());
        setNewMappings(mappings.notSatisfying());
    }

    private List<String> handlerNames(List<Mapping> mappings) {
        return (List<String>) collect(mappings, new Transformer() {
            @Override
            public Object transform(Object o) {
                Mapping mapping = (Mapping) o;
                return getHandlerName(mapping);
            }
        });
    }

    protected ServletHandler servletHandler() {
        return servletHandler;
    }

    abstract PredicatedPartition<Mapping> partitionMappingsBy(String pathSpec);

    abstract void setNewMappings(List<Mapping> mappings);

    abstract PredicatedPartition<Holder> partitionHoldersBy(List<String> handlerNames);

    abstract void setNewHolders(List<Holder> holders);

    abstract String getHandlerName(Mapping mapping);
}
