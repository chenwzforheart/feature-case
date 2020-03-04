package com.dubbo.provider;

import com.alibaba.dubbo.container.Main;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class ProviderApplication {

    private static final Logger logger = LoggerFactory.getLogger(ProviderApplication.class);

    public static void main(String[] args) {
        //1.日志加载控制
        String env = System.getProperty("env");
        logger.info("当前环境：{}", env);
        String path = env == null ? "" : '-' + env;
        PropertyConfigurator.configure(ProviderApplication.class.getClassLoader().getResourceAsStream(String.format("log4j%s.properties", path)));

        //2.配置切换控制通过#{systemProperties['env']} 读取
        logger.debug("provider 启动了");
        Main.main(args);
    }
}
