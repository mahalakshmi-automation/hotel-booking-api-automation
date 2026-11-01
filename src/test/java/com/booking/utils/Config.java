package com.booking.utils;

import java.io.InputStream;
import java.util.Properties;

public final class Config {
    private static final Properties PROPERTIES = new Properties();
    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) throw new IllegalStateException("Missing config.properties");
            PROPERTIES.load(input);
        } catch (Exception e) { throw new ExceptionInInitializerError(e); }
    }
    private Config() {}
    public static String get(String key) {
        String override = System.getProperty(key);
        if (override == null) override = System.getenv(key.toUpperCase().replace('.', '_'));
        String value = override != null ? override : PROPERTIES.getProperty(key);
        if (value == null) throw new IllegalArgumentException("Missing configuration: " + key);
        return value;
    }
    public static int getInt(String key) { return Integer.parseInt(get(key)); }
}
