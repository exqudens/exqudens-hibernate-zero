package com.exqudens.hibernate.test.util;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import groovy.util.ConfigObject;
import groovy.util.ConfigSlurper;

public class ConfigGroovy implements Supplier<Map<String, Object>> {

    public static String[] DATA_SOURCE_ALL_PROPERTY_KEYS;
    public static String[] DATA_SOURCE_REMOVE_PROPERTY_KEYS;

    static {
        DATA_SOURCE_ALL_PROPERTY_KEYS = new String[] {
            "protocol",
            "subProtocol",

            "host",
            "port",
            "dbName",
            "jdbcUrlParams",

            "username",
            "password",
            "driverClassName",
            "connectionTimeout",
            "readOnly",
            "maximumPoolSize",
            "jdbcUrl"
        };
        DATA_SOURCE_REMOVE_PROPERTY_KEYS = new String[] {
            "protocol",
            "subProtocol",
            "host",
            "port",
            "dbName",
            "jdbcUrlParams"
        };
    }

    private final Map<String, Object> config;

    public ConfigGroovy(String environment, String groovyString) {
        super();

        ConfigSlurper configSlurper = new ConfigSlurper(environment != null ? environment : "ALL");
        ConfigObject configObject = configSlurper.parse(groovyString);

        Map<?, ?> rawMap = configObject.flatten();

        Map<String, Object> map = rawMap.entrySet().stream().map(
            e -> new SimpleEntry<String, Object>(e.getKey().toString(), e.getValue())
        ).collect(HashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), HashMap::putAll);

        this.config = Collections.unmodifiableMap(map);
    }

    @Override
    public Map<String, Object> get() {
        return config;
    }

    public Map<String, Object> retrieveProperties(String prefix) {
        Map<String, Object> properties = config.entrySet().stream().filter(e -> e.getKey().startsWith(prefix)).map(
            e -> {
                String newKey = e.getKey().replace(prefix, "");
                return new SimpleEntry<>(newKey, e.getValue());
            }
        ).collect(HashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), HashMap::putAll);

        return properties;
    }

    public Properties createDataSourceProperties(
        String defaultsPrefix,
        String prefix,
        Function<String, String> passwordDecodeFunction
    ) {
        Map<String, Object> defaultMap = retrieveProperties(defaultsPrefix);
        Map<String, Object> map = retrieveProperties(prefix);

        for (String key : DATA_SOURCE_ALL_PROPERTY_KEYS) {
            Object value = map.get(key) != null ? map.get(key).toString() : null;
            Object defaultValue = defaultMap.get(key);
            if (value != null) {
                if ("password".equals(key)) {
                    map.put(key, passwordDecodeFunction.apply(value.toString()));
                }
            } else if (defaultValue != null) {
                map.put(key, defaultValue);
            }
        }

        String key = "jdbcUrl";
        Object value = map.get(key) != null ? map.get(key).toString() : null;
        if (value == null) {
            value = Stream.of(
                map.get("protocol"),
                ":",
                map.get("subProtocol"),
                "://",
                map.get("host"),
                ":",
                map.get("port"),
                "/",
                map.get("dbName"),
                map.get("jdbcUrlParams"),
                "&failOverReadOnly=" + !Boolean.valueOf(map.get("readOnly").toString())
            ).map(Object::toString).collect(Collectors.joining());
        }
        map.put(key, value);

        Stream.of(DATA_SOURCE_REMOVE_PROPERTY_KEYS).forEach(k -> map.remove(k));

        Properties p = new Properties();
        p.putAll(map);
        return p;
    }

}
