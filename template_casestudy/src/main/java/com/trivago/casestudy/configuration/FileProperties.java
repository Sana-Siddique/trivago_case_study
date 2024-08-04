package com.trivago.casestudy.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class is used to configure paths of different files
 *
 */


@Component
public class FileProperties {

    @Value("${file.jsonPath}")
    private String jsonFilePath;

    @Value("${file.yamlPath}")
    private String yamlFilePath;

    public String getJsonFilePath() {
        return jsonFilePath;
    }

    public String getYamlFilePath() {
        return yamlFilePath;
    }

}
