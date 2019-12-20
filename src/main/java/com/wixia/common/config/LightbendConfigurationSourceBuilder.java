package com.wixia.common.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigResolveOptions;

/**
 * Builder for {@link LightbendConfigurationSource}. It initializes and keeps the
 * parameters (configuration) that are used to configure the LightbendConfigurationSource.
 */
public class LightbendConfigurationSourceBuilder {

    // These variables control how the load strategy is chosen
    private String resourceBasename;
    private ClassLoader classLoader;
    private Config customConfig;
    private ConfigResolveOptions configResolveOptions;
    private ConfigParseOptions configParseOptions;

    // These variables controls wether to use a system property or not
    private String systemPropertyKey;
    private String systemPropertyValue;

    // A prefix that can be used or not
    private String prefix;

    /**
     * Build a {@link LightbendConfigurationSource} using this builder's configuration parameters.
     *
     * @return new {@link LightbendConfigurationSource}
     */
    public LightbendConfigurationSource build() {
        final LightbendLoadStrategy loadStrategy = createLoadStrategy();

        final LightbendConfigFactoryHandler configFactoryHandler = new LightbendConfigFactoryHandler(loadStrategy, prefix);
        if (isSystemPropertySet()) {
            configFactoryHandler.withSystemProperty(systemPropertyKey, systemPropertyValue);
        }
        return new LightbendConfigurationSource(configFactoryHandler);
    }

    /**
     * Set the resource basename for Lightbend Config
     *
     * (see: https://doc.akka.io/docs/akka/current/general/configuration.html#reading-configuration-from-a-custom-location
     * and {@link com.typesafe.config.ConfigFactory#load(String)})
     *
     * @param resourceBasename name (optionally without extension) of a resource on classpath
     * @return this builder with resource basename set to {@code resourceBasename}
     */
    public LightbendConfigurationSourceBuilder withResourceBasename(String resourceBasename) {
        this.resourceBasename = resourceBasename;
        return this;
    }

    /**
     * Set the Class Loader to use for Lightbend Config
     *
     * (see: https://doc.akka.io/docs/akka/current/general/configuration.html#a-word-about-classloaders
     * and {@link com.typesafe.config.ConfigFactory#load(ClassLoader)})
     *
     * @param classLoader class loader for finding resources
     * @return this builder with {@link ClassLoader} set to {@code classLoader}
     */
    public LightbendConfigurationSourceBuilder withClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
        return this;
    }

    /**
     * Set the custom configuration to use for Lightbend Config
     *
     * (see: https://doc.akka.io/docs/akka/current/general/configuration.html#where-configuration-is-read-from
     * and {@link com.typesafe.config.ConfigFactory#load(Config)}
     *
     * @param config the application's portion of the configuration
     * @return this builder with {@link Config} set to {@code config}
     */
    public LightbendConfigurationSourceBuilder withConfig(Config config) {
        this.customConfig = config;
        return this;
    }

    /**
     * Like {@link LightbendConfigurationSourceBuilder#withConfig(Config)} but allows you
     * to specify {@link ConfigResolveOptions}
     *
     * (see: {@link com.typesafe.config.ConfigFactory#load(Config, ConfigResolveOptions)}
     * and {@link ConfigResolveOptions})
     *
     * @param config the application's portion of the configuration
     * @param resolveOptions options for resolving the assembled config stack
     * @return this builder with {@link Config} set to {@code config} and {@link ConfigResolveOptions} set to {@code resolveOptions}
     */
    public LightbendConfigurationSourceBuilder withConfigResolveOptions(
            Config config,
            ConfigResolveOptions resolveOptions) {
        this.customConfig = config;
        this.configResolveOptions = resolveOptions;
        return this;
    }

    /**
     * Set the parse options
     *
     * (see: {@link ConfigParseOptions} and
     * {@link com.typesafe.config.ConfigFactory#load(ConfigParseOptions)}
     *
     * @param parseOptions Options for parsing resources
     * @return this builder with {@link ConfigParseOptions} set to {@code parseOptions}
     */
    public LightbendConfigurationSourceBuilder withConfigParseOptions(ConfigParseOptions parseOptions) {
        this.configParseOptions = parseOptions;
        return this;
    }

    /**
     * Set the prefix to use when parsing the configuration information. This is used instead of (or together with)
     * the environment functionality in cfg4j.
     *
     * @param prefix the string representation that is used in the configuration file as a prefix to configurations
     * @return this builder with prefix set to {@code prefix}
     */
    public LightbendConfigurationSourceBuilder withPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * Sets the URL that the configuration gets loaded from, using Lightbend Config's
     * way of directing load source, using system properties
     *
     * (See: https://doc.akka.io/docs/akka/current/general/configuration.html#reading-configuration-from-a-custom-location)
     *
     * @param configUrl the URL pointing to a configuration resource
     * @return this builder initialised to load configuration from an URL
     */
    public LightbendConfigurationSourceBuilder withConfigUrl(String configUrl) {
        systemPropertyKey = "config.url";
        systemPropertyValue = configUrl;

        return this;
    }

    /**
     * Sets the file that the configuration gets loaded from, using Lightbend Config's
     * way of directing load source, using system properties
     *
     * (See: https://doc.akka.io/docs/akka/current/general/configuration.html#reading-configuration-from-a-custom-location)
     *
     * @param configFile the file pointer to a configuration resource
     * @return this builder initialised to load configuration from a file
     */
    public LightbendConfigurationSourceBuilder withConfigFile(String configFile) {
        systemPropertyKey = "config.file";
        systemPropertyValue = configFile;

        return this;
    }

    /**
     * Sets the resource that the configuration gets loaded from, using Lightbend Config's
     * way of directing load source, using system properties
     *
     * (See: https://doc.akka.io/docs/akka/current/general/configuration.html#reading-configuration-from-a-custom-location)
     *
     * @param configResource the resource pointer to a configuration resource
     * @return this builder initialised to load configuration from a resource
     */
    public LightbendConfigurationSourceBuilder withConfigResource(String configResource) {
        systemPropertyKey = "config.resource";
        systemPropertyValue = configResource;

        return this;
    }

    public String getResourceBasename() {
        return resourceBasename;
    }

    public void setResourceBasename(String resourceBasename) {
        this.resourceBasename = resourceBasename;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public Config getCustomConfig() {
        return customConfig;
    }

    public void setCustomConfig(Config customConfig) {
        this.customConfig = customConfig;
    }

    public ConfigResolveOptions getConfigResolveOptions() {
        return configResolveOptions;
    }

    public void setConfigResolveOptions(ConfigResolveOptions configResolveOptions) {
        this.configResolveOptions = configResolveOptions;
    }

    public ConfigParseOptions getConfigParseOptions() {
        return configParseOptions;
    }

    public void setConfigParseOptions(ConfigParseOptions configParseOptions) {
        this.configParseOptions = configParseOptions;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isResourceBasenameSet() {
        return resourceBasename != null;
    }

    public boolean isClassLoaderSet() {
        return classLoader != null;
    }

    public boolean isCustomConfigSet() {
        return customConfig != null;
    }

    public boolean isConfigResolveOptionsSet() {
        return configResolveOptions != null;
    }

    public boolean isConfigParseOptionsSet() {
        return configParseOptions != null;
    }

    public boolean isSystemPropertySet() { return systemPropertyKey != null; }

    @Override
    public String toString() {
        return String.format(
                "%s {resourceBasename=%s, classLoader=%s, customConfig=%s, configResolveOptions=%s, configParseOptions=%s}",
                this.getClass().getName(),
                resourceBasename, classLoader, customConfig, configResolveOptions, configParseOptions);
    }

    /**
     * This is the mapping between the builder design pattern used in cfg4j and the factory pattern used in
     * Lightbend.
     * <p>
     * This maybe overly complicated but I did not want to use reflection in the
     * {@link LightbendConfigFactoryHandler} class.
     *
     * @return the load strategy for the parameters that are set currently
     */
    LightbendLoadStrategy createLoadStrategy() {
        StrategyType type = createStrategyType();

        if (type == null) {
            throw new IllegalStateException(
                    String.format("Could not get loading strategy from the current state %s", this.toString()));
        }

        LightbendLoadStrategy loadStrategy;

        switch (type) {
            case DEFAULT:
                loadStrategy = new LightbendConfigFactoryHandler.DefaultLoadStrategy();
                break;
            case CLASSLOADER:
                loadStrategy = new LightbendConfigFactoryHandler.ClassLoaderLoadStrategy(
                        classLoader);
                break;
            case CLASSLOADERCUSTOMCONFIG:
                loadStrategy = new LightbendConfigFactoryHandler.ClassLoaderCustomConfigLoadStrategy(
                        classLoader, customConfig);
                break;
            case CLASSLOADERCUSTOMCONFIGRESOLVEOPTIONS:
                loadStrategy = new LightbendConfigFactoryHandler.ClassLoaderCustomConfigResolveOptionsLoadStrategy(
                        classLoader, customConfig, configResolveOptions);
                break;
            case CLASSLOADERPARSEOPTIONS:
                loadStrategy = new LightbendConfigFactoryHandler.ClassLoaderParseOptionsLoadStrategy(
                        classLoader, configParseOptions);
                break;
            case CLASSLOADERPARSEOPTIONSRESOLVEOPTIONS:
                loadStrategy = new LightbendConfigFactoryHandler.ClassLoaderParseOptionsResolveOptionsLoadStrategy(
                        classLoader, configParseOptions, configResolveOptions);
                break;
            case CLASSLOADERRESOLVEOPTIONS:
                loadStrategy = new LightbendConfigFactoryHandler.ClassLoaderResolveOptionsLoadStrategy(
                        classLoader, configResolveOptions);
                break;
            case CLASSLOADERRESOURCEBASENAME:
                loadStrategy = new LightbendConfigFactoryHandler.ClassLoaderResourceBasenameLoadStrategy(
                        classLoader, resourceBasename);
                break;
            case CLASSLOADERRESOURCEBASENAMEPARSEOPTIONSRESOLVEOPTIONS:
                loadStrategy = new LightbendConfigFactoryHandler.
                        ClassLoaderResourceBasenameParseOptionsResolveOptionsLoadStrategy(
                        classLoader, resourceBasename, configParseOptions, configResolveOptions);
                break;
            case CUSTOMCONFIG:
                loadStrategy = new LightbendConfigFactoryHandler.CustomConfigLoadStrategy(
                        customConfig);
                break;
            case CUSTOMCONFIGRESOLVEOPTIONS:
                loadStrategy = new LightbendConfigFactoryHandler.CustomConfigResolveOptionsLoadStrategy(
                        customConfig, configResolveOptions);
                break;
            case PARSEOPTIONS:
                loadStrategy = new LightbendConfigFactoryHandler.ParseOptionsLoadStrategy(
                        configParseOptions);
                break;
            case PARSEOPTIONSRESOLVEOPTIONS:
                loadStrategy = new LightbendConfigFactoryHandler.ParseOptionsResolveOptionsLoadStrategy(
                        configParseOptions, configResolveOptions);
                break;
            case RESOURCEBASENAME:
                loadStrategy = new LightbendConfigFactoryHandler.ResourceBasenameLoadStrategy(
                        resourceBasename);
                break;
            case RESOURCEBASENAMEPARSEOPTIONSRESOLVEOPTIONS:
                loadStrategy = new LightbendConfigFactoryHandler.ResourceBasenameParseOptionsResolveOptionsLoadStrategy(
                        resourceBasename, configParseOptions, configResolveOptions);
                break;
            default:
                throw new IllegalStateException(
                        String.format(
                                "Could not instantiate load strategy with the current state %s", this.toString()));
        }

        return loadStrategy;
    }

    public StrategyType createStrategyType() {
        int result = Flags.DEFAULT.getSetWeight(!(
                isResourceBasenameSet()
                        || isClassLoaderSet()
                        || isCustomConfigSet()
                        || isConfigResolveOptionsSet()
                        || isConfigParseOptionsSet()));
        result += Flags.RESOURCEBASENAME.getSetWeight(isResourceBasenameSet());
        result += Flags.CLASSLOADER.getSetWeight(isClassLoaderSet());
        result += Flags.CUSTOMCONFIG.getSetWeight(isCustomConfigSet());
        result += Flags.CONFIGRESOLVEOPTIONS.getSetWeight(isConfigResolveOptionsSet());
        result += Flags.CONFIGPARSEOPTIONS.getSetWeight(isConfigParseOptionsSet());

        return StrategyType.typeOf(result);
    }

    enum Flags {
        DEFAULT(0),
        RESOURCEBASENAME(1),
        CLASSLOADER(2),
        CUSTOMCONFIG(3),
        CONFIGRESOLVEOPTIONS(4),
        CONFIGPARSEOPTIONS(5);

        final int weight;

        Flags(int w) {
            this.weight = (int) Math.pow(2, w);
        }

        public int getSetWeight(boolean isSet) {
            return isSet ? weight : 0;
        }

    }

    enum StrategyType {
        DEFAULT(Flags.DEFAULT.weight),
        CLASSLOADER(Flags.CLASSLOADER.weight),
        CLASSLOADERCUSTOMCONFIG(Flags.CLASSLOADER.weight + Flags.CUSTOMCONFIG.weight),
        CLASSLOADERCUSTOMCONFIGRESOLVEOPTIONS(Flags.CLASSLOADER.weight + Flags.CUSTOMCONFIG.weight + Flags.CONFIGRESOLVEOPTIONS.weight),
        CLASSLOADERPARSEOPTIONS(Flags.CLASSLOADER.weight + Flags.CONFIGPARSEOPTIONS.weight),
        CLASSLOADERPARSEOPTIONSRESOLVEOPTIONS(Flags.CLASSLOADER.weight + Flags.CONFIGPARSEOPTIONS.weight + Flags.CONFIGRESOLVEOPTIONS.weight),
        CLASSLOADERRESOLVEOPTIONS(Flags.CLASSLOADER.weight + Flags.CONFIGRESOLVEOPTIONS.weight),
        CLASSLOADERRESOURCEBASENAME(Flags.CLASSLOADER.weight + Flags.RESOURCEBASENAME.weight),
        CLASSLOADERRESOURCEBASENAMEPARSEOPTIONSRESOLVEOPTIONS(Flags.CLASSLOADER.weight + Flags.RESOURCEBASENAME.weight + Flags.CONFIGRESOLVEOPTIONS.weight),
        CUSTOMCONFIG(Flags.CUSTOMCONFIG.weight),
        CUSTOMCONFIGRESOLVEOPTIONS(Flags.CUSTOMCONFIG.weight + Flags.CONFIGRESOLVEOPTIONS.weight),
        PARSEOPTIONS(Flags.CONFIGPARSEOPTIONS.weight),
        PARSEOPTIONSRESOLVEOPTIONS(Flags.CONFIGPARSEOPTIONS.weight + Flags.CONFIGRESOLVEOPTIONS.weight),
        RESOURCEBASENAME(Flags.RESOURCEBASENAME.weight),
        RESOURCEBASENAMEPARSEOPTIONSRESOLVEOPTIONS(Flags.RESOURCEBASENAME.weight + Flags.CONFIGPARSEOPTIONS.weight + Flags.CONFIGRESOLVEOPTIONS.weight),
        ;

        final int typeValue;

        StrategyType(int val) {
            this.typeValue = val;
        }

        public static StrategyType typeOf(int val) {
            for (StrategyType type : values()) {
                if (type.typeValue == val) {
                    return type;
                }
            }
            return null;
        }
    }
}
