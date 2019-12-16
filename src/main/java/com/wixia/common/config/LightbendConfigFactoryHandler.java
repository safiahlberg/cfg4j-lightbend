package com.wixia.common.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigResolveOptions;

public class LightbendConfigFactoryHandler implements ConfigFactoryHandler {

    private final LightbendLoadStrategy loadStrategy;

    public LightbendConfigFactoryHandler(
            LightbendLoadStrategy loadStrategy) {
        this.loadStrategy = loadStrategy;
    }

    @Override
    public Config init() {
        return loadStrategy.load();
    }

    @Override
    public Config reload() {
        return loadStrategy.load();
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
            this.classLoader = classLoader;
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
            this.classLoader = classLoader;
            this.customConfig = customConfig;
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
            this.classLoader = classLoader;
            this.customConfig = customConfig;
            this.configResolveOptions = configResolveOptions;
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
            this.classLoader = classLoader;
            this.configParseOptions = configParseOptions;
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
            this.classLoader = classLoader;
            this.configParseOptions = configParseOptions;
            this.configResolveOptions = configResolveOptions;
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
            this.classLoader = classLoader;
            this.configResolveOptions = configResolveOptions;
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
            this.classLoader = classLoader;
            this.resourceBasename = resourceBasename;
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
            this.classLoader = classLoader;
            this.resourceBasename = resourceBasename;
            this.configParseOptions = parseOptions;
            this.configResolveOptions = resolveOptions;
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
            this.customConfig = customConfig;
            this.resolveOptions = resolveOptions;
        }

        @Override
        public Config load() {
            return ConfigFactory.load(customConfig, resolveOptions);
        }
    }

    static class ParseOptionsLoadStrategy implements LightbendLoadStrategy {

        private final ConfigParseOptions parseOptions;

        ParseOptionsLoadStrategy(ConfigParseOptions parseOptions) {
            this.parseOptions = parseOptions;
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
            this.parseOptions = parseOptions;
            this.resolveOptions = resolveOptions;
        }

        @Override
        public Config load() {
            return ConfigFactory.load(parseOptions, resolveOptions);
        }
    }

    static class ResourceBasenameLoadStrategy implements LightbendLoadStrategy {

        private final String resourceBasename;

        ResourceBasenameLoadStrategy(String resourceBasename) {
            this.resourceBasename = resourceBasename;
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
            this.resourceBasename = resourceBasename;
            this.parseOptions = parseOptions;
            this.resolveOptions = resolveOptions;
        }

        @Override
        public Config load() {
            return ConfigFactory.load(resourceBasename, parseOptions, resolveOptions);
        }
    }
}

