package com.thoughtworks.webstub.config;

public interface ConfigurationListener {
    void configurationCreated(HttpConfiguration configuration);
    void configurationCleared();
}
