package com.basia.config;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class YamlParser {

    public static LocalConfig getConfig() {
        Yaml yaml = new Yaml(new Constructor(LocalConfig.class));
        InputStream inputStream = YamlParser.class.getClassLoader().getResourceAsStream("properties.yml");
        return yaml.load(inputStream);
    }
}
