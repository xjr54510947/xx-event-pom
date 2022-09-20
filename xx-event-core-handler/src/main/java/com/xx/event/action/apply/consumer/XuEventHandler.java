package com.xx.event.action.apply.consumer;

import com.xx.event.entity.XuMessage;

/**
 * @Author: X_X
 * @Date: 2022/9/18 - 09 - 18 - 15:24
 * @Description: com.xx.event.action.apply.consumer
 * @version: 1.0
 *
 * 生产者去实现这个接口，然后可以消费经过拦截处理的消息
 */
public interface XuEventHandler<T> {
    void eventHandler(T data,XuMessage xuMessage);
}
