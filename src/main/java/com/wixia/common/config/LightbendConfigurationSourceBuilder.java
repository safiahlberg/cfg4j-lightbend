package com.wixia.common.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigResolveOptions;

public class LightbendConfigurationSourceBuilder {

    private String resourceBasename;
    private ClassLoader classLoader;
    private Config customConfig;
    private ConfigResolveOptions configResolveOptions;
    private ConfigParseOptions configParseOptions;

    public LightbendConfigurationSource build() {
        return new LightbendConfigurationSource(new LightbendConfigFactoryHandler(resourceBasename,
                classLoader,
                customConfig,
                configResolveOptions,
                configParseOptions));
    }
}
