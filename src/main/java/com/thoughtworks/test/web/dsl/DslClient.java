package com.thoughtworks.test.web.dsl;

import com.thoughtworks.test.web.config.StubConfiguration;

public interface DslClient {
    void configurationCreated(StubConfiguration configuration);
}
