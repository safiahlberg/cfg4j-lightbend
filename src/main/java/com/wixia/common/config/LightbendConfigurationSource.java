package com.wixia.common.config;

import com.typesafe.config.Config;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.context.environment.Environment;

import java.util.Properties;

import static java.util.Objects.requireNonNull;

public class LightbendConfigurationSource implements ConfigurationSource {

    private final ConfigFactoryHandler configFactoryHandler;
    private Config lightbendConfig;

    private boolean initialized;

    LightbendConfigurationSource(ConfigFactoryHandler configFactoryHandler) {
        this.configFactoryHandler = requireNonNull(configFactoryHandler);
        init();
    }

    @Override
    public Properties getConfiguration(Environment environment) {
        if (!initialized) {
            throw new IllegalStateException(
                    "Configuration source has to be successfully initialized before you request configuration.");
        }

        reload();

        Properties config = new Properties();
        lightbendConfig.entrySet().stream().forEach(entry -> config.put(entry.getKey(), entry.getValue()));

        return config;
    }

    @Override
    public void init() {
        lightbendConfig = configFactoryHandler.init();
        initialized = true;
    }

    @Override
    public void reload() {
        lightbendConfig = configFactoryHandler.reload();
    }
}
