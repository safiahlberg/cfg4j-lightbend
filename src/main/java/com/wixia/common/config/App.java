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

        conf = conf.getConfig("pref1");

        System.out.println(String.format("compound1.val1=%s", conf.getString("compound1.val1")));
        System.out.println(String.format("compound1.val2=%s", conf.getString("compound1.val2")));
    }
}
