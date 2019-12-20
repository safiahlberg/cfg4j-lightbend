package com.wixia.common.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;
import mockit.*;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;

import static org.testng.Assert.*;

@Test
public class LightbendConfigurationSourceTest {

    @Tested
    LightbendConfigurationSource tested;
    @Injectable
    ConfigFactoryHandler mockConfigFactoryHandler;

    @Test(expectedExceptions = NullPointerException.class)
    public void testConstructorWithNull() {
        new LightbendConfigurationSource(null);
    }

    @Test
    public void testConstructorAndInit() {
        LightbendConfigurationSource configurationSource = new LightbendConfigurationSource(mockConfigFactoryHandler);

        assertNotNull(configurationSource);

        new Verifications() {{
            mockConfigFactoryHandler.init();
            times = 2;
        }};
    }

    @Test
    public void testInit() {
        tested.init();

        new Verifications() {{
            mockConfigFactoryHandler.init();
            times = 2;
        }};
    }

    @Test
    public void testReload() {
        tested.reload();

        new Verifications() {{
            mockConfigFactoryHandler.init();
            times = 1;

            mockConfigFactoryHandler.reload();
            times = 1;
        }};
    }
}
