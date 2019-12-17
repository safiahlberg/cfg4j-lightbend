package com.wixia.common.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigResolveOptions;

import static java.util.Objects.requireNonNull;

public class LightbendConfigFactoryHandler implements ConfigFactoryHandler {

    private final LightbendLoadStrategy loadStrategy;
    private String prefix;

    public LightbendConfigFactoryHandler(
            LightbendLoadStrategy loadStrategy, String prefix) {
        this.loadStrategy = loadStrategy;
        this.prefix = prefix;
    }

    @Override
    public Config init() {
        final Config defaultConfig = loadStrategy.load();

        if (prefix != null) {
            return defaultConfig.getConfig(prefix).withFallback(defaultConfig);
        }

        return defaultConfig;
    }

    @Override
    public Config reload() {
        return init();
    }

    static class DefaultLoadStrategy implements LightbendLoadStrategy {

        @Override
        public Config load() {
            return ConfigFactory.load();
        }
    }

    static class ClassLoaderLoadStrategy implements LightbendLoadStrategy {
        private final ClassLoader classLoader;

        ClassLoaderLoadStrategy(ClassLoader classLoader) {
            this.classLoader = requireNonNull(classLoader, "Argument 'classLoader' must not be null");
        }

        @Override
        public Config load() {
            return ConfigFactory.load(classLoader);
        }
    }

    static class ClassLoaderCustomConfigLoadStrategy implements LightbendLoadStrategy {
        private final ClassLoader classLoader;
        private final Config customConfig;

        ClassLoaderCustomConfigLoadStrategy(ClassLoader classLoader, Config customConfig) {
            this.classLoader = requireNonNull(classLoader, "Argument 'classLoader' must not be null");
            this.customConfig = requireNonNull(customConfig, "Argument 'customConfig' must not be null");
        }

        @Override
        public Config load() {
            return ConfigFactory.load(classLoader, customConfig);
        }
    }

    static class ClassLoaderCustomConfigResolveOptionsLoadStrategy implements LightbendLoadStrategy {
        private final ClassLoader classLoader;
        private final Config customConfig;
        private final ConfigResolveOptions configResolveOptions;

        ClassLoaderCustomConfigResolveOptionsLoadStrategy(ClassLoader classLoader, Config customConfig,
                                                          ConfigResolveOptions configResolveOptions) {
            this.classLoader = requireNonNull(classLoader, "Argument 'classLoader' must not be null");
            this.customConfig = requireNonNull(customConfig, "Argument 'customConfig' must not be null");
            this.configResolveOptions = requireNonNull(configResolveOptions, "Argument 'configResolveOptions' must not be null");
        }

        @Override
        public Config load() {
            return ConfigFactory.load(classLoader, customConfig, configResolveOptions);
        }
    }

    static class ClassLoaderParseOptionsLoadStrategy implements LightbendLoadStrategy {
        private final ClassLoader classLoader;
        private final ConfigParseOptions configParseOptions;

        ClassLoaderParseOptionsLoadStrategy(ClassLoader classLoader, ConfigParseOptions configParseOptions) {
            this.classLoader = requireNonNull(classLoader, "Argument 'classLoader' must not be null");
            this.configParseOptions = requireNonNull(configParseOptions, "Argument 'configParseOptions' must not be null");
        }

        @Override
        public Config load() {
            return ConfigFactory.load(classLoader, configParseOptions);
        }
    }

    static class ClassLoaderParseOptionsResolveOptionsLoadStrategy implements LightbendLoadStrategy {
        private final ClassLoader classLoader;
        private final ConfigParseOptions configParseOptions;
        private final ConfigResolveOptions configResolveOptions;

        ClassLoaderParseOptionsResolveOptionsLoadStrategy(ClassLoader classLoader,
                                                          ConfigParseOptions configParseOptions,
                                                          ConfigResolveOptions configResolveOptions) {
            this.classLoader = requireNonNull(classLoader, "Argument 'classLoader' must not be null");
            this.configParseOptions = requireNonNull(configParseOptions, "Argument 'configParseOptions' must not be null");
            this.configResolveOptions = requireNonNull(configResolveOptions, "Argument 'configResolveOptions' must not be null");
        }

        @Override
        public Config load() {
            return ConfigFactory.load(classLoader, configParseOptions, configResolveOptions);
        }
    }

    static class ClassLoaderResolveOptionsLoadStrategy implements LightbendLoadStrategy {
        private final ClassLoader classLoader;
        private final ConfigResolveOptions configResolveOptions;

        ClassLoaderResolveOptionsLoadStrategy(ClassLoader classLoader, ConfigResolveOptions configResolveOptions) {
            this.classLoader = requireNonNull(classLoader, "Argument 'classLoader' must not be null");
            this.configResolveOptions = requireNonNull(configResolveOptions, "Argument 'configResolveOptions' must not be null");
        }

        @Override
        public Config load() {
            return ConfigFactory.load(classLoader, configResolveOptions);
        }
    }

    static class ClassLoaderResourceBasenameLoadStrategy implements LightbendLoadStrategy {
        private final ClassLoader classLoader;
        private final String resourceBasename;

        ClassLoaderResourceBasenameLoadStrategy(ClassLoader classLoader, String resourceBasename) {
            this.classLoader = requireNonNull(classLoader, "Argument 'classLoader' must not be null");
            this.resourceBasename = requireNonNull(resourceBasename, "Argument 'resourceBasename' must not be null");
        }

        @Override
        public Config load() {
            return ConfigFactory.load(classLoader, resourceBasename);
        }
    }

    static class ClassLoaderResourceBasenameParseOptionsResolveOptionsLoadStrategy implements LightbendLoadStrategy {
        private final ClassLoader classLoader;
        private final String resourceBasename;
        private final ConfigParseOptions configParseOptions;
        private final ConfigResolveOptions configResolveOptions;

        ClassLoaderResourceBasenameParseOptionsResolveOptionsLoadStrategy(
                ClassLoader classLoader, String resourceBasename,
                ConfigParseOptions parseOptions,
                ConfigResolveOptions resolveOptions) {
            this.classLoader = requireNonNull(classLoader, "Argument 'classLoader' must not be null");
            this.resourceBasename = requireNonNull(resourceBasename, "Argument 'resourceBasename' must not be null");
            this.configParseOptions = requireNonNull(parseOptions, "Argument 'parseOptions' must not be null");
            this.configResolveOptions = requireNonNull(resolveOptions, "Argument 'resolveOptions' must not be null");
        }

        @Override
        public Config load() {
            return ConfigFactory.load(classLoader, resourceBasename, configParseOptions, configResolveOptions);
        }
    }

    static class CustomConfigLoadStrategy implements LightbendLoadStrategy {

        private final Config customConfig;

        CustomConfigLoadStrategy(Config customConfig) {
            this.customConfig = customConfig;
        }

        @Override
        public Config load() {
            return ConfigFactory.load(customConfig);
        }
    }

    static class CustomConfigResolveOptionsLoadStrategy implements LightbendLoadStrategy {

        private final Config customConfig;
        private final ConfigResolveOptions resolveOptions;

        CustomConfigResolveOptionsLoadStrategy(Config customConfig, ConfigResolveOptions resolveOptions) {
            this.customConfig = requireNonNull(customConfig, "Argument 'customConfig' must not be null");
            this.resolveOptions = requireNonNull(resolveOptions, "Argument 'resolveOptions' must not be null");
        }

        @Override
        public Config load() {
            return ConfigFactory.load(customConfig, resolveOptions);
        }
    }

    static class ParseOptionsLoadStrategy implements LightbendLoadStrategy {

        private final ConfigParseOptions parseOptions;

        ParseOptionsLoadStrategy(ConfigParseOptions parseOptions) {
            this.parseOptions = requireNonNull(parseOptions, "Argument 'parseOptions' must not be null");
        }

        @Override
        public Config load() {
            return ConfigFactory.load(parseOptions);
        }
    }

    static class ParseOptionsResolveOptionsLoadStrategy implements LightbendLoadStrategy {

        private final ConfigParseOptions parseOptions;
        private final ConfigResolveOptions resolveOptions;

        ParseOptionsResolveOptionsLoadStrategy(ConfigParseOptions parseOptions, ConfigResolveOptions resolveOptions) {
            this.parseOptions = requireNonNull(parseOptions, "Argument 'parseOptions' must not be null");
            this.resolveOptions = requireNonNull(resolveOptions, "Argument 'resolveOptions' must not be null");
        }

        @Override
        public Config load() {
            return ConfigFactory.load(parseOptions, resolveOptions);
        }
    }

    static class ResourceBasenameLoadStrategy implements LightbendLoadStrategy {

        private final String resourceBasename;

        ResourceBasenameLoadStrategy(String resourceBasename) {
            this.resourceBasename = requireNonNull(resourceBasename, "Argument 'resourceBasename' must not be null");
        }

        @Override
        public Config load() {
            return ConfigFactory.load(resourceBasename);
        }
    }

    static class ResourceBasenameParseOptionsResolveOptionsLoadStrategy implements LightbendLoadStrategy {

        private final String resourceBasename;
        private final ConfigParseOptions parseOptions;
        private final ConfigResolveOptions resolveOptions;

        ResourceBasenameParseOptionsResolveOptionsLoadStrategy(String resourceBasename,
                                                               ConfigParseOptions parseOptions,
                                                               ConfigResolveOptions resolveOptions) {
            this.resourceBasename = requireNonNull(resourceBasename, "Argument 'resourceBasename' must not be null");
            this.parseOptions = requireNonNull(parseOptions, "Argument 'parseOptions' must not be null");
            this.resolveOptions = requireNonNull(resolveOptions, "Argument 'resolveOptions' must not be null");
        }

        @Override
        public Config load() {
            return ConfigFactory.load(resourceBasename, parseOptions, resolveOptions);
        }
    }
}

