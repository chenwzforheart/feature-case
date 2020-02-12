package com.dubbo.provider;

import org.apache.dubbo.configcenter.DynamicConfiguration;
import org.apache.dubbo.configcenter.support.zookeeper.ZookeeperDynamicConfigurationFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mc
 * @date 2020-02-12.
 */
@Configuration
public class ConfigCenter {

    @Bean
    public DynamicConfiguration zookeeperDynamicConfiguration() {
        DynamicConfiguration dynamicConfiguration = new ZookeeperDynamicConfigurationFactory().getDynamicConfiguration(org.apache.dubbo.common.URL.valueOf("dubbo://localhost:2181"));
        return dynamicConfiguration;
    }
}
