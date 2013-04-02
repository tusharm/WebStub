package com.thoughtworks.webstub.stub;

import com.thoughtworks.webstub.config.HttpConfiguration;
import com.thoughtworks.webstub.stub.matcher.RequestPartMatcher;
import com.thoughtworks.webstub.utils.Predicate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static org.apache.commons.collections.CollectionUtils.select;

class Configurations {
    private final List<HttpConfiguration> configurations;

    Configurations() {
        this.configurations = asList();
    }

    Configurations(List<HttpConfiguration> configurations) {
        this.configurations = unmodifiableList(configurations);
    }

    Configurations add(HttpConfiguration configuration) {
        List<HttpConfiguration> newConfigs = new ArrayList<HttpConfiguration>(configurations);
        newConfigs.add(configuration);
        return new Configurations(newConfigs);
    }

    Configurations filterBy(final RequestPartMatcher matcher) throws ConfigurationNotFoundException {
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
            throw new ConfigurationNotFoundException(matcher);

        return new Configurations(filtered);
    }


    List<HttpConfiguration> all() {
        return configurations;
    }
}
