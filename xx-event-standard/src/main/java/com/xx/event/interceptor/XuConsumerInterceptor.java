package com.xx.event.interceptor;

import com.xx.event.entity.XuMessage;

/**
 * @Author: X_X
 * @Date: 2022/9/18 - 09 - 18 - 17:52
 * @Description: com.xx.event.interceptor
 * @version: 1.0
 */
public interface XuConsumerInterceptor {
    /**
     * 设置一个是否需要拦截器的方法判断
     */
    boolean isSupport(XuMessage xuMessage);

    /**
     * 自定义一个拦截器
     * 如果返回值为null，则不返回消息，表示中断发送
     */
    XuMessage consumerInterceptor(XuMessage xuMessage);
}
