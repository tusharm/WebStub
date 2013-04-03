package com.thoughtworks.webstub.config;

public abstract class ConfigurationProvider {
    private ConfigurationListener listener;

    protected ConfigurationProvider(ConfigurationListener listener) {
        this.listener = listener;
    }

    public void configurationCreated(HttpConfiguration configuration) {
        listener.configurationCreated(configuration);
    }
}
