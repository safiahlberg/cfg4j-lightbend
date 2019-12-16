package com.wixia.common.config;

import com.typesafe.config.Config;

public interface ConfigFactoryHandler {

    Config init();

    Config reload();

}
