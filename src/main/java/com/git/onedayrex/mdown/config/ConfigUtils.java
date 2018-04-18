package com.git.onedayrex.mdown.config;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by fuxiang.zhong on 2018/2/23.
 */
public class ConfigUtils {
    private static final Logger logger = Logger.getLogger(ConfigUtils.class);

    private static final Properties properties = new Properties();

    static {
        InputStream resourceAsStream = ConfigUtils.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            logger.error("load config error:" + e);
        }
    }

    public static String getPropertie(String key) {
        return properties.getProperty(key);
    }
}
