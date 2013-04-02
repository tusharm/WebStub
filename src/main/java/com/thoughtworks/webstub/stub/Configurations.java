package com.thoughtworks.webstub.stub;

import com.thoughtworks.webstub.config.HttpConfiguration;
import com.thoughtworks.webstub.stub.matcher.RequestPartMatcher;
import com.thoughtworks.webstub.utils.Predicate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.select;

class Configurations {
    private final List<HttpConfiguration> configurations;

    Configurations() {
        this.configurations = new ArrayList<HttpConfiguration>();
    }

    Configurations(List<HttpConfiguration> configurations) {
        this.configurations = new ArrayList<HttpConfiguration>(configurations);
    }

    void add(HttpConfiguration configuration) {
        configurations.add(configuration);
    }

    Configurations filterBy(final RequestPartMatcher matcher) throws MissingMatchingConfigurationException {
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


    List<HttpConfiguration> all() {
        return Collections.unmodifiableList(configurations);
    }

    HttpConfiguration first() {
        return configurations.get(0);
    }
}
