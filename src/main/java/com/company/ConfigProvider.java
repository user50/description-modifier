package com.company;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by user50 on 27.06.2015.
 */
public class ConfigProvider {

    public Config get(){
        return new Config(getProperties());
    }

    private Properties getProperties(){
        Properties prop = new Properties();

        try (InputStream input = new FileInputStream("config/config.properties")) {
            prop.load(input);
        }
        catch (FileNotFoundException ex) {
            throw new RuntimeException("Unable to find config/config.properties file");
        } catch (IOException e) {
            throw new RuntimeException("Unable to read config/config.properties file. " + e.getMessage());
        }

        return prop;
    }
}
