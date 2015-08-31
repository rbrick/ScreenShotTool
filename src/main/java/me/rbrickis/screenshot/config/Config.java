package me.rbrickis.screenshot.config;

import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

// TODO: Add configuration.
public class Config {
    @Getter private String clientId;

    public Config() throws IOException {
        Properties properties = new Properties();
        FileInputStream stream = new FileInputStream(new File("./imgur.properties"));
        properties.load(stream);

        clientId = properties.getProperty("client-id");
    }

}
