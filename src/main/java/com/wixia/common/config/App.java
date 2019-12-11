package com.wixia.common.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Config conf = ConfigFactory.load();

        System.out.println(String.format("globalVal=%s", conf.getString("globalVal")));
        System.out.println(String.format("global.withExtension=%s", conf.getString("global.withExtension")));
        System.out.println(String.format("prefix.globalVal=%s", conf.getString("prefix.globalVal")));

        System.out.println(String.format("compound1.val1=%s", conf.getString("compound1.val1")));
        System.out.println(String.format("compound1.val2=%s", conf.getString("compound1.val2")));

        // This resets the config to use everything prefixed with pref1, but in this case, the global values are lost
        System.out.println("\nReset the configuration to point at pref1");
        conf = conf.getConfig("pref1");

        System.out.println(String.format("compound1.val1=%s", conf.getString("compound1.val1")));
        System.out.println(String.format("compound1.val2=%s", conf.getString("compound1.val2")));

        // This would throw an exception
        // System.out.println(String.format("globalVal=%s", conf.getString("globalVal")));

        // This resets the config to use everything prefixed with pref1 and use other config as fallback
        conf = ConfigFactory.load();
        System.out.println("\nReload configuration");
        conf = conf.getConfig("pref1").withFallback(conf);

        System.out.println(String.format("compound1.val1=%s", conf.getString("compound1.val1")));
        System.out.println(String.format("compound1.val2=%s", conf.getString("compound1.val2")));

        // This can now be done without an exception
        System.out.println(String.format("globalVal=%s", conf.getString("globalVal")));

    }
}
