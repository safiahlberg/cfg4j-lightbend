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
        LightbendLoadStrategy loadStrategy = createLoadStrategy();

        return new LightbendConfigurationSource(new LightbendConfigFactoryHandler(loadStrategy));
    }

    public LightbendConfigurationSourceBuilder withResourceBasename(String resourceBasename) {
        this.resourceBasename = resourceBasename;
        return this;
    }

    public LightbendConfigurationSourceBuilder withClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
        return this;
    }

    public LightbendConfigurationSourceBuilder withConfig(Config config) {
        this.customConfig = config;
        return this;
    }

    public LightbendConfigurationSourceBuilder withConfigResolveOptions(ConfigResolveOptions resolveOptions) {
        this.configResolveOptions = resolveOptions;
        return this;
    }

    public LightbendConfigurationSourceBuilder withConfigParseOptions(ConfigParseOptions parseOptions) {
        this.configParseOptions = parseOptions;
        return this;
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

    @Override
    public String toString() {
        return String.format(
                "%s {resourceBasename=%s, classLoader=%s, customConfig=%s, configResolveOptions=%s, configParseOptions=%s}",
                this.getClass().getName(), resourceBasename, classLoader, customConfig, configResolveOptions, configParseOptions);
    }

    public LightbendLoadStrategy createLoadStrategy() {
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
        int result = Flags.DEFAULT.getSetWeight(true);
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
            this.weight = 2 ^ w;
        }

        public int getWeight() {
            return weight;
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
