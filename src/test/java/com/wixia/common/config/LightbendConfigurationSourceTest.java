package com.wixia.common.config;

import com.typesafe.config.Config;
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
    @Mocked
    Config mockConfig;

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
    public void testGetConfiguration() {
        final HashMap<String, String> configMap = new HashMap<>();
        configMap.put("key1", "value1");
        configMap.put("key2", "value2");

        new Expectations() {{
            mockConfig.entrySet();
            result = Collections.unmodifiableSet(configMap.entrySet());
        }};

        Properties configProperties = tested.getConfiguration(null);

        assertTrue(configProperties.containsKey("key1"));
        assertTrue(configProperties.containsKey("key2"));
        assertEquals(configProperties.size(), 2);

        new Verifications() {{
            mockConfigFactoryHandler.reload();
            times = 1;

            mockConfig.entrySet();
            times = 1;
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
