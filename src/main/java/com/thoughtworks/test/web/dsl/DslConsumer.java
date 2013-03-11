package com.thoughtworks.test.web.dsl;

import com.thoughtworks.test.web.config.StubConfiguration;

public interface DslConsumer {
    void configurationCreated(StubConfiguration configuration);
}
