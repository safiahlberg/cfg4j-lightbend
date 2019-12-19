package com.wixia.common.config;

import com.typesafe.config.Config;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.context.environment.Environment;

import java.util.Properties;

import static java.util.Objects.requireNonNull;

/**
 * Note: use {@link LightbendConfigurationSourceBuilder} for building instances of this class.
 * <p>
 * Read configuration using Lightbend Config, see https://github.com/lightbend/config
 */
public class LightbendConfigurationSource implements ConfigurationSource {

    private final ConfigFactoryHandler configFactoryHandler;
    private Config lightbendConfig;

    private boolean initialized;

    /**
     * Note: use {@link LightbendConfigurationSourceBuilder} for building instances of this class.
     * <p>
     * Read configuration using Lightbend Config, which has various ways to read configurations, this can
     * be configured with the builder class.
     *
     * @param configFactoryHandler {@link LightbendConfigFactoryHandler} the handler class that helps
     *                             with creating the load strategy
     */
    LightbendConfigurationSource(ConfigFactoryHandler configFactoryHandler) {
        this.configFactoryHandler = requireNonNull(configFactoryHandler, "Argument 'configFactoryHandler' must not be null");
        init();
    }

    /**
     * (Re)loads the configuration ({@link Config}) using Lightbend, and converts it to a
     * local {@link Properties} object.
     *
     * @param environment {@Link org.cfg4j.source.context.environment.Environment} not used
     * @return the populated {@link Properties} object
     */
    @Override
    public Properties getConfiguration(Environment environment) {
        if (!initialized) {
            throw new IllegalStateException(
                    "Configuration source has to be successfully initialized before you request configuration.");
        }

        reload();

        Properties config = new Properties();
        lightbendConfig.entrySet().stream().forEach(entry -> config.put(entry.getKey(), entry.getValue().unwrapped()));

        return config;
    }

    /**
     * Use the {@link LightbendConfigFactoryHandler#init()} to create a
     * Lightbend {@link Config} object, which is cached.
     */
    @Override
    public void init() {
        lightbendConfig = configFactoryHandler.init();
        initialized = true;
    }

    /**
     * Use the {@link LightbendConfigFactoryHandler#reload()} to create a
     * Lightbend {@link Config} object, which is cached.
     */
    @Override
    public void reload() {
        lightbendConfig = configFactoryHandler.reload();
    }
}
