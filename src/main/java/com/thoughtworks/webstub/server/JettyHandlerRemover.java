package com.thoughtworks.webstub.server;

import com.thoughtworks.webstub.utils.PredicatedPartition;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;

import java.util.List;

import static org.apache.commons.lang.ArrayUtils.contains;

abstract class JettyHandlerRemover<Mapping, Holder> {
    private ServletHandler servletHandler;

    JettyHandlerRemover(ServletContextHandler contextHandler) {
        servletHandler = contextHandler.getServletHandler();
    }

    void remove(String pathSpec) {
        PredicatedPartition<Mapping> mappings = partitionMappingsBy(pathSpec);
        if (mappings.satisfying().isEmpty())
            return;

        String handlerNameFromMapping = getHandlerName(mappings.satisfying().get(0));
        PredicatedPartition<Holder> holders = partitionHoldersBy(handlerNameFromMapping);

        setNewHolders(holders.notSatisfying());
        setNewMappings(mappings.notSatisfying());
    }

    protected ServletHandler servletHandler() {
        return servletHandler;
    }

    abstract PredicatedPartition<Mapping> partitionMappingsBy(String pathSpec);

    abstract void setNewMappings(List<Mapping> mappings);

    abstract PredicatedPartition<Holder> partitionHoldersBy(String pathSpec);

    abstract void setNewHolders(List<Holder> holders);

    abstract String getHandlerName(Mapping mapping);
}
