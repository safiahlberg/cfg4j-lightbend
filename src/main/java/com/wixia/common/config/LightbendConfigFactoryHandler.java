package com.wixia.common.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigResolveOptions;

public class LightbendConfigFactoryHandler implements ConfigFactoryHandler {

    private final String resourceBasename;
    private final ClassLoader classLoader;
    private final Config customConfig;
    private final ConfigResolveOptions configResolveOptions;
    private final ConfigParseOptions configParseOptions;

    public LightbendConfigFactoryHandler(
            String resourceBasename,
            ClassLoader classLoader,
            Config customConfig,
            ConfigResolveOptions configResolveOptions,
            ConfigParseOptions configParseOptions) {

        this.resourceBasename = resourceBasename;
        this.classLoader = classLoader;
        this.customConfig = customConfig;
        this.configParseOptions = configParseOptions;
        this.configResolveOptions = configResolveOptions;
    }

    @Override
    public Config init() {
        return ConfigFactory.load();
    }

    @Override
    public Config reload() {
        return ConfigFactory.load();
    }
}
