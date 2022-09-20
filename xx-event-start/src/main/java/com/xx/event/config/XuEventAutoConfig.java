package com.xx.event.config;

import com.xx.event.action.apply.consumer.XuEventHandler;
import com.xx.event.action.core.consumer.DefaultCoreConsumerHandler;
import com.xx.event.action.core.producer.DefaultCoreProducerHandler;
import com.xx.event.utils.ApplicationUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: X_X
 * @Date: 2022/9/18 - 09 - 18 - 18:28
 * @Description: com.xx.event.config
 * @version: 1.0
 */
@Configuration
public class XuEventAutoConfig {

    @Bean
    public DefaultCoreProducerHandler getDefaultCoreProducerHandler(){
        return new DefaultCoreProducerHandler();
    }

    @Bean
    @ConditionalOnBean(XuEventHandler.class)
    public DefaultCoreConsumerHandler getDefaultCoreConsumerHandler(){
        return new DefaultCoreConsumerHandler();
    }

    @Bean
    public ApplicationUtils getApplicationUtils(){
        return new ApplicationUtils();
    }
}
