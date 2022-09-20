package com.xx.event.action.core.producer;

import com.xx.event.entity.XuMessage;
import com.xx.event.inter.CoreProducerStandard;
import com.xx.event.inter.MqProducerStandard;
import com.xx.event.interceptor.XuProducerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: X_X
 * @Date: 2022/9/18 - 09 - 18 - 13:13
 * @Description: com.xx.event.action.core.producer
 * @version: 1.0
 */

@Slf4j
public class DefaultCoreProducerHandler implements CoreProducerStandard {

    @Autowired(required = false)
    private List<XuProducerInterceptor> producerInterceptors;

    @Autowired
    private MqProducerStandard mqProducerStandard;

    @Override
    public void sendMessage(XuMessage xuMessage) {
        log.info("核心服务接收到要发送给mq的消息:{}",xuMessage);
        //调用生产者的拦截器链，对消息进行处理
            if (producerInterceptors != null){
                for (XuProducerInterceptor interceptor : producerInterceptors){
                    try {
                        if (!interceptor.isSupport(xuMessage)){ continue; }
                        //到这里说明有拦截器要拦截发送到消息
                        XuMessage message = interceptor.producerInterceptor(xuMessage);
                        if (message==null){
                            //为空说明该消息，某种原因被拦截，不能发送
                            log.info("该消息已经被拦截！");
                            return;
                        }
                    }catch (Exception e){
                        log.info("拦截器发生了异常，已经捕获！");
                    }

                }
            }
        //把消息发送到MQ中
        mqProducerStandard.sendMessage2Mq(xuMessage);
    }
}
