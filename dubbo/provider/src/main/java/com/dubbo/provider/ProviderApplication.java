package com.dubbo.provider;

import com.alibaba.dubbo.container.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProviderApplication {

    private static final Logger logger = LoggerFactory.getLogger(ProviderApplication.class);

    public static void main(String[] args) {
        logger.info("provider 启动了");
        Main.main(args);
    }
}
