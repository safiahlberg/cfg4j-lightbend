package com.wixia.common.config;

import com.typesafe.config.Config;

public interface LightbendLoadStrategy {
    Config load();
}
