package com.wixia.common.config;

import com.typesafe.config.Config;

/**
 * This interface represents the capability of {@link org.cfg4j.source.ConfigurationSource}
 * with methods for init() and reload().
 */
public interface ConfigFactoryHandler {

    Config init();

    Config reload();

}
