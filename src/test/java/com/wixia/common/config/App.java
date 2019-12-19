package com.wixia.common.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.wixia.common.config.testconfig.Compound1Config;
import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;

/**
 *
 */
public class App {
    public static void main(String[] args) {
        Config conf = ConfigFactory.load();

        System.out.println(String.format("globalVal=%s", conf.getString("globalVal")));
        System.out.println(String.format("global.withExtension=%s", conf.getString("global.withExtension")));
        System.out.println(String.format("prefix.globalVal=%s", conf.getString("prefix.globalVal")));

        System.out.println(String.format("compound1.val1=%s", conf.getString("compound1.val1")));
        System.out.println(String.format("compound1.val2=%s", conf.getString("compound1.val2")));
        System.out.println(String.format("compound1.val3 with substitution=%s", conf.getString("compound1.val3")));

        // This resets the config to use everything prefixed with pref1, but in this case, the global values are lost
        System.out.println("\nReset the configuration to point at pref1");
        conf = conf.getConfig("pref1");

        System.out.println(String.format("compound1.val1=%s", conf.getString("compound1.val1")));
        System.out.println(String.format("compound1.val2=%s", conf.getString("compound1.val2")));
        System.out.println(String.format("compound1.val3 with substitution=%s", conf.getString("compound1.val3")));

        System.out.printf("compound1.url1=%s%n", conf.getString("compound1.url1"));
        System.out.printf("compound1.url2=%s%n", conf.getString("compound1.url2"));

        // This would throw an exception
        // System.out.println(String.format("globalVal=%s", conf.getString("globalVal")));

        // This resets the config to use everything prefixed with pref1 and use other config as fallback
        conf = ConfigFactory.load();
        System.out.println("\nReload configuration");
        conf = conf.getConfig("pref1").withFallback(conf);

        System.out.println(String.format("compound1.val1=%s", conf.getString("compound1.val1")));
        System.out.println(String.format("compound1.val2=%s", conf.getString("compound1.val2")));
        System.out.println(String.format("compound1.val3 with substitution=%s", conf.getString("compound1.val3")));

        System.out.printf("compound1.url1=%s%n", conf.getString("compound1.url1"));
        System.out.printf("compound1.url2=%s%n", conf.getString("compound1.url2"));

        // This can now be done without an exception
        System.out.println(String.format("globalVal=%s", conf.getString("globalVal")));

        // Try to integrate Lightbend with cfg4j
        ConfigurationSource configurationSource = new LightbendConfigurationSourceBuilder()
                .withPrefix("pref1")
                // .withResourceBasename("application.conf")
                // .withClassLoader(conf.getClass().getClassLoader())
                .build();
        ConfigurationProvider configurationProvider = new ConfigurationProviderBuilder()
                .withConfigurationSource(configurationSource).build();

        Compound1Config compound1Config = configurationProvider.bind("compound1", Compound1Config.class);

        System.out.printf("With cfg4j. compound1.val1=%s%n", compound1Config.val1());
        System.out.printf("With cfg4j. compound1.val2=%s%n", compound1Config.val2());
        System.out.printf("With cfg4j. compound1.val3=%s%n", compound1Config.val3());

        System.out.printf("With cfg4j. compound1.url1=%s%n", compound1Config.url1().toString());
        System.out.printf("With cfg4j. compound1.url2=%s%n", compound1Config.url2().toString());
    }
}
