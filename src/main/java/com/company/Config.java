package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by user50 on 20.06.2015.
 */
public class Config {

    Properties properties;

    public Config(Properties properties) {
        this.properties = properties;
    }

    public String getInputFile()
    {
        return properties.getProperty("inputFile");
    }

    public String getOutputDir()
    {
        return properties.getProperty("outputDir");
    }

    public String getEncoding()
    {
        return properties.getProperty("encoding");
    }

    public boolean isModifyDescription()
    {
        return Boolean.parseBoolean(properties.getProperty("modifyDescription"));
    }

    public boolean isModifyId()
    {
        return Boolean.parseBoolean(properties.getProperty("modifyId"));
    }

    public String getPrefix()
    {
        return properties.getProperty("idPrefix");
    }

    public String getTemplate()
    {
        try {
            return new String(Files.readAllBytes(Paths.get("config/template.html")), "utf-8");
        } catch (IOException e) {
            throw new RuntimeException("Unable to find config/template.html file. ");
        }
    }

    public int getFilesCount(){
        int filesCount = Integer.valueOf(properties.getProperty("filesCount"));

        if (filesCount <= 0)
            throw new IllegalArgumentException("filesCount has to be a positive integer");

        return filesCount;
    }
}
