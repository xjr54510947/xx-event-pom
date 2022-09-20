package com.xx.event.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * @Author: X_X
 * @Date: 2022/9/18 - 09 - 18 - 16:53
 * @Description: com.xx.event.utils
 * @version: 1.0
 */
@Component
public class ApplicationUtils implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    private static BeanDefinitionRegistry registry;

    private static ApplicationContext context;

    public static<T> T getBean(Class<T> bean){
        return context.getBean(bean);
    }

    //手动注册bean对象
    public static void registerBean(String beanName,Object bean){
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(bean.getClass(), new Supplier() {
            @Override
            public Object get() {
                return bean;
            }

        });
        registry.registerBeanDefinition(beanName,builder.getBeanDefinition());
    }




    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        ApplicationUtils.registry=beanDefinitionRegistry;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationUtils.context = applicationContext;
    }
}
