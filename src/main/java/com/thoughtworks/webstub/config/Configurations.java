package com.thoughtworks.webstub.config;

import com.thoughtworks.webstub.context.matcher.RequestPartMatcher;
import com.thoughtworks.webstub.utils.Predicate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.select;

public class Configurations {
    private final List<HttpConfiguration> configurations;

    public Configurations() {
        this(new ArrayList<HttpConfiguration>());
    }

    public Configurations(List<HttpConfiguration> configurations) {
        this.configurations = new ArrayList<HttpConfiguration>(configurations);
    }

    public void add(HttpConfiguration configuration) {
        configurations.add(configuration);
    }

    public void reset() {
        configurations.clear();
    }

    public List<HttpConfiguration> all() {
        return Collections.unmodifiableList(configurations);
    }

    public Configurations filterBy(final RequestPartMatcher matcher) throws MissingMatchingConfigurationException {
        List<HttpConfiguration> filtered = (List<HttpConfiguration>) select(configurations, new Predicate<HttpConfiguration>() {
            @Override
            public boolean satisfies(HttpConfiguration configuration) {
                try {
                    return matcher.matches(configuration);
                } catch (IOException e) {
                    return false;
                }
            }
        });

        if (filtered.isEmpty())
            throw new MissingMatchingConfigurationException(matcher);

        return new Configurations(filtered);
    }

    public HttpConfiguration last() {
        return configurations.get(configurations.size() - 1);
    }
}
