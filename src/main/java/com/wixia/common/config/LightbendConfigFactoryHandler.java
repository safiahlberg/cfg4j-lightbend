package com.wixia.common.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigResolveOptions;

import static java.util.Objects.requireNonNull;

/**
 * A helper class for creation of Lightbend {@link Config} instances with
 * arguments set to {@link LightbendLoadStrategy} classes. These load strategies represent
 * different arguments and in turn different calls to the overloaded load method in {@link ConfigFactory}
 *
 * This is a bridge from the builder design pattern used in cfg4j to the factory pattern used in Lightbend Config.
 */
public class LightbendConfigFactoryHandler implements ConfigFactoryHandler {

    private final LightbendLoadStrategy loadStrategy;
    private String prefix;

    public LightbendConfigFactoryHandler(
            LightbendLoadStrategy loadStrategy) {
        this.loadStrategy = requireNonNull(loadStrategy, "Argument 'loadStrategy' must not be null");
    }

    public LightbendConfigFactoryHandler(
            LightbendLoadStrategy loadStrategy, String prefix) {
        this(loadStrategy);
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

    /**
     * Default load strategy, used when no arguments are set. It will call {@link ConfigFactory#load()}
     */
    static class DefaultLoadStrategy implements LightbendLoadStrategy {

        /**
         * Call {@link ConfigFactory#load()}
         *
         * @return {@link Config} object from Lightbend
         */
        @Override
        public Config load() {
            return ConfigFactory.load();
        }
    }

    /**
     * Classloader load strategy. Uses a specified classloader. It will call {@link ConfigFactory#load(ClassLoader)}
     */
    static class ClassLoaderLoadStrategy implements LightbendLoadStrategy {
        private final ClassLoader classLoader;

        ClassLoaderLoadStrategy(ClassLoader classLoader) {
            this.classLoader = requireNonNull(classLoader, "Argument 'classLoader' must not be null");
        }

        /**
         * Call {@link ConfigFactory#load(ClassLoader)}
         *
         * @return {@link Config} object from Lightbend
         */
        @Override
        public Config load() {
            return ConfigFactory.load(classLoader);
        }
    }

    /**
     * Classloader and custom Config load strategy. Uses a specified classloader. 
     * It will call {@link ConfigFactory#load(ClassLoader, Config)}
     */
    static class ClassLoaderCustomConfigLoadStrategy implements LightbendLoadStrategy {
        private final ClassLoader classLoader;
        private final Config customConfig;

        ClassLoaderCustomConfigLoadStrategy(ClassLoader classLoader, Config customConfig) {
            this.classLoader = requireNonNull(classLoader, "Argument 'classLoader' must not be null");
            this.customConfig = requireNonNull(customConfig, "Argument 'customConfig' must not be null");
        }

        /**
         * Call {@link ConfigFactory#load(ClassLoader, Config)}
         *
         * @return {@link Config} object from Lightbend
         */
        @Override
        public Config load() {
            return ConfigFactory.load(classLoader, customConfig);
        }
    }

    /**
     * Classloader, custom Config and ConfigResolveOptions load strategy.
     * It will call {@link ConfigFactory#load(ClassLoader, Config, ConfigResolveOptions)}
     */
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

        /**
         * Call {@link ConfigFactory#load(ClassLoader, Config, ConfigResolveOptions)}
         *
         * @return {@link Config} object from Lightbend
         */
        @Override
        public Config load() {
            return ConfigFactory.load(classLoader, customConfig, configResolveOptions);
        }
    }

    /**
     * Classloader and ConfigParseOptions load strategy.
     * It will call {@link ConfigFactory#load(ClassLoader, ConfigParseOptions)}
     */
    static class ClassLoaderParseOptionsLoadStrategy implements LightbendLoadStrategy {
        private final ClassLoader classLoader;
        private final ConfigParseOptions configParseOptions;

        ClassLoaderParseOptionsLoadStrategy(ClassLoader classLoader, ConfigParseOptions configParseOptions) {
            this.classLoader = requireNonNull(classLoader, "Argument 'classLoader' must not be null");
            this.configParseOptions = requireNonNull(configParseOptions, "Argument 'configParseOptions' must not be null");
        }

        /**
         * Call {@link ConfigFactory#load(ClassLoader, ConfigParseOptions)}
         *
         * @return {@link Config} object from Lightbend
         */
        @Override
        public Config load() {
            return ConfigFactory.load(classLoader, configParseOptions);
        }
    }

    /**
     * Classloader, ConfigParseOptions and ConfigResolveOptions load strategy.
     * It will call {@link ConfigFactory#load(ClassLoader, ConfigParseOptions, ConfigResolveOptions)}
     */
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

        /**
         * Call {@link ConfigFactory#load(ClassLoader, ConfigParseOptions, ConfigResolveOptions)}
         *
         * @return {@link Config} object from Lightbend
         */
        @Override
        public Config load() {
            return ConfigFactory.load(classLoader, configParseOptions, configResolveOptions);
        }
    }

    /**
     * Classloader and ConfigResolveOptions load strategy.
     * It will call {@link ConfigFactory#load(ClassLoader, ConfigResolveOptions)}
     */
    static class ClassLoaderResolveOptionsLoadStrategy implements LightbendLoadStrategy {
        private final ClassLoader classLoader;
        private final ConfigResolveOptions configResolveOptions;

        ClassLoaderResolveOptionsLoadStrategy(ClassLoader classLoader, ConfigResolveOptions configResolveOptions) {
            this.classLoader = requireNonNull(classLoader, "Argument 'classLoader' must not be null");
            this.configResolveOptions = requireNonNull(configResolveOptions, "Argument 'configResolveOptions' must not be null");
        }

        /**
         * Call {@link ConfigFactory#load(ClassLoader, ConfigResolveOptions)}
         *
         * @return {@link Config} object from Lightbend
         */
        @Override
        public Config load() {
            return ConfigFactory.load(classLoader, configResolveOptions);
        }
    }

    /**
     * Classloader and resource basename load strategy.
     * It will call {@link ConfigFactory#load(ClassLoader, String)}
     */
    static class ClassLoaderResourceBasenameLoadStrategy implements LightbendLoadStrategy {
        private final ClassLoader classLoader;
        private final String resourceBasename;

        ClassLoaderResourceBasenameLoadStrategy(ClassLoader classLoader, String resourceBasename) {
            this.classLoader = requireNonNull(classLoader, "Argument 'classLoader' must not be null");
            this.resourceBasename = requireNonNull(resourceBasename, "Argument 'resourceBasename' must not be null");
        }

        /**
         * Call {@link ConfigFactory#load(ClassLoader, String)}
         *
         * @return {@link Config} object from Lightbend
         */
        @Override
        public Config load() {
            return ConfigFactory.load(classLoader, resourceBasename);
        }
    }

    /**
     * Classloader, resource basename, ConfigParseOptions, ConfigResolveOptions load strategy.
     * It will call {@link ConfigFactory#load(ClassLoader, String, ConfigParseOptions, ConfigResolveOptions)}
     */
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

        /**
         * Call {@link ConfigFactory#load(ClassLoader, String, ConfigParseOptions, ConfigResolveOptions)}
         *
         * @return {@link Config} object from Lightbend
         */
        @Override
        public Config load() {
            return ConfigFactory.load(classLoader, resourceBasename, configParseOptions, configResolveOptions);
        }
    }

    /**
     * Custom Config load strategy.
     * It will call {@link ConfigFactory#load(Config)}
     */
    static class CustomConfigLoadStrategy implements LightbendLoadStrategy {

        private final Config customConfig;

        CustomConfigLoadStrategy(Config customConfig) {
            this.customConfig = customConfig;
        }

        /**
         * Call {@link ConfigFactory#load(Config)}
         *
         * @return {@link Config} object from Lightbend
         */
        @Override
        public Config load() {
            return ConfigFactory.load(customConfig);
        }
    }

    /**
     * Custom Config, ConfigResolveOptions load strategy.
     * It will call {@link ConfigFactory#load(Config, ConfigResolveOptions)}
     */
    static class CustomConfigResolveOptionsLoadStrategy implements LightbendLoadStrategy {

        private final Config customConfig;
        private final ConfigResolveOptions resolveOptions;

        CustomConfigResolveOptionsLoadStrategy(Config customConfig, ConfigResolveOptions resolveOptions) {
            this.customConfig = requireNonNull(customConfig, "Argument 'customConfig' must not be null");
            this.resolveOptions = requireNonNull(resolveOptions, "Argument 'resolveOptions' must not be null");
        }

        /**
         * Call {@link ConfigFactory#load(Config, ConfigResolveOptions)}
         *
         * @return {@link Config} object from Lightbend
         */
        @Override
        public Config load() {
            return ConfigFactory.load(customConfig, resolveOptions);
        }
    }

    /**
     * ConfigParseOptions load strategy.
     * It will call {@link ConfigFactory#load(ConfigParseOptions)}
     */
    static class ParseOptionsLoadStrategy implements LightbendLoadStrategy {

        private final ConfigParseOptions parseOptions;

        ParseOptionsLoadStrategy(ConfigParseOptions parseOptions) {
            this.parseOptions = requireNonNull(parseOptions, "Argument 'parseOptions' must not be null");
        }

        /**
         * Call {@link ConfigFactory#load(ConfigParseOptions)}
         *
         * @return {@link Config} object from Lightbend
         */
        @Override
        public Config load() {
            return ConfigFactory.load(parseOptions);
        }
    }

    /**
     * ConfigParseOptions and ConfigResolveOptions load strategy.
     * It will call {@link ConfigFactory#load(ConfigParseOptions, ConfigResolveOptions)}
     */
    static class ParseOptionsResolveOptionsLoadStrategy implements LightbendLoadStrategy {

        private final ConfigParseOptions parseOptions;
        private final ConfigResolveOptions resolveOptions;

        ParseOptionsResolveOptionsLoadStrategy(ConfigParseOptions parseOptions, ConfigResolveOptions resolveOptions) {
            this.parseOptions = requireNonNull(parseOptions, "Argument 'parseOptions' must not be null");
            this.resolveOptions = requireNonNull(resolveOptions, "Argument 'resolveOptions' must not be null");
        }

        /**
         * Call {@link ConfigFactory#load(ConfigParseOptions, ConfigResolveOptions)}
         *
         * @return {@link Config} object from Lightbend
         */
        @Override
        public Config load() {
            return ConfigFactory.load(parseOptions, resolveOptions);
        }
    }

    /**
     * Resource Basename load strategy.
     * It will call {@link ConfigFactory#load(String resourceBasename)}
     */
    static class ResourceBasenameLoadStrategy implements LightbendLoadStrategy {

        private final String resourceBasename;

        ResourceBasenameLoadStrategy(String resourceBasename) {
            this.resourceBasename = requireNonNull(resourceBasename, "Argument 'resourceBasename' must not be null");
        }

        /**
         * Call {@link ConfigFactory#load(String resourceBasename)}
         *
         * @return {@link Config} object from Lightbend
         */
        @Override
        public Config load() {
            return ConfigFactory.load(resourceBasename);
        }
    }

    /**
     * Resource Basename, ConfigParseOptions, ConfigResolveOptions load strategy.
     * It will call {@link ConfigFactory#load(String resourceBasename, ConfigParseOptions, ConfigResolveOptions)}
     */
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

        /**
         * Call {@link ConfigFactory#load(String resourceBasename, ConfigParseOptions, ConfigResolveOptions)}
         *
         * @return {@link Config} object from Lightbend
         */
        @Override
        public Config load() {
            return ConfigFactory.load(resourceBasename, parseOptions, resolveOptions);
        }
    }
}

