package com.cwzsmile.tool;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by csh9016 on 2019/4/30.
 */
public class BuildDal {

    public static void main(String[] args) {
        List<String> warnings = new ArrayList<String>();
        final boolean overwrite = true;
        File configFile = new File("dal/src/main/resources/generatorConfig.xml");
        System.out.println("config file is in : " + configFile.getAbsoluteFile());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration configuration;
        try {
            configuration = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            CustomGenerator mybatisGenerator = new CustomGenerator(configuration, callback, warnings);
            mybatisGenerator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main1(String[] args) {
        List<String> warnings = new ArrayList<String>();
        final boolean overwrite = true;
        File configFile = new File("dal/src/main/resources/generatorConfig.xml");
        System.out.println("config file is in : " + configFile.getAbsoluteFile());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration configuration;
        try {
            configuration = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator mybatisGenerator = new MyBatisGenerator(configuration, callback, warnings);
            mybatisGenerator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
