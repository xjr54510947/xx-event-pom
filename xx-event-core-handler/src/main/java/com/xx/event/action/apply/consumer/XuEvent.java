package com.xx.event.action.apply.consumer;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Author: X_X
 * @Date: 2022/9/18 - 09 - 18 - 15:19
 * @Description: com.xx.event.action.apply.consumer
 * @version: 1.0
 * 定义一个注解，然后消费者去使用这个注解，获得该事件的名称，以便于根据名称取设置交换机的direct
 * Documented这个注解？？
 */
@Documented
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface XuEvent {

    String value();
}
