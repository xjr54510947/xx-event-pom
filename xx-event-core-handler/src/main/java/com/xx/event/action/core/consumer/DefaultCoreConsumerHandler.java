package com.xx.event.action.core.consumer;

import com.xx.event.action.apply.consumer.XuEvent;
import com.xx.event.action.apply.consumer.XuEventHandler;
import com.xx.event.entity.XuMessage;
import com.xx.event.inter.CoreConsumerStandard;
import com.xx.event.interceptor.XuConsumerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: X_X
 * @Date: 2022/9/18 - 09 - 18 - 17:36
 * @Description: com.xx.event.action.core.consumer
 * @version: 1.0
 */
@Slf4j
public class DefaultCoreConsumerHandler implements CoreConsumerStandard {

    @Autowired(required = false)
    private List<XuConsumerInterceptor> coreConsumerStandards;

    @Autowired
    private List<XuEventHandler> xuEventHandlers;

    @Override
    public void sendMessage(XuMessage xuMessage) {
        //这里我把xuMessage改为了coreConsumerStandards
        if (xuMessage!=null){
            if (coreConsumerStandards!=null){
                for (XuConsumerInterceptor c: coreConsumerStandards){
                    try {
                        if (!c.isSupport(xuMessage)){continue;}

                        //这个消息是经过拦截器处理完的
                        xuMessage =  c.consumerInterceptor(xuMessage);

                        //如果消息为空，说明拦截器不让它放行
                        if (xuMessage==null){
                            log.info("消费者拦截器拦截消息，不让这条消息放行!");
                            return;
                        }
                    }catch (Exception e){
                        log.info("发生消费者拦截器发生异常，已经被捕获--{}",e);
                    }
                }
            }

            for (XuEventHandler e : xuEventHandlers) {
                //获得消息的事件类型
                String eventType = xuMessage.getEventType();
                XuEvent xuEvent = e.getClass().getAnnotation(XuEvent.class);

                if (xuEvent == null) {
                    continue;
                }

                if (eventType.equals(xuEvent.value())) {
                    e.eventHandler(xuMessage.getData(), xuMessage);
                    break;
                }
            }
        }

    }


}
