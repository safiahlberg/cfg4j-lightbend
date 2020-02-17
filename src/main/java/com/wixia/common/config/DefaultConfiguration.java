package com.wixia.common.config;

import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;

public class DefaultConfiguration {

    protected final ConfigurationSource configurationSource = new LightbendConfigurationSourceBuilder()
            .build();

    protected final ConfigurationProvider configurationProvider = new ConfigurationProviderBuilder()
            .withConfigurationSource(configurationSource).build();

    public ConfigurationProvider getConfigurationProvider() {
        return configurationProvider;
    }
}
