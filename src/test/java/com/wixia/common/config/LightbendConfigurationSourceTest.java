package com.wixia.common.config;

import org.testng.annotations.Test;

@Test
public class LightbendConfigurationSourceTest {

    @Test(expectedExceptions = NullPointerException.class)
    public void testConstructorWithNull() {
        new LightbendConfigurationSource(null);
    }

    @Test
    public void testGetConfiguration() {
    }

    @Test
    public void testInit() {
    }

    @Test
    public void testReload() {
    }
}
