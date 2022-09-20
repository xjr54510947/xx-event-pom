package com.xx.event.action.mq.rabbitMQ.consumer;

import com.xx.event.base.Constant;
import com.xx.event.entity.XuMessage;
import com.xx.event.inter.CoreConsumerStandard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: X_X
 * @Date: 2022/9/18 - 09 - 18 - 17:39
 * @Description: com.xx.event.action.mq.rabbitMQ.consumer
 * @version: 1.0
 */
@Slf4j
public class ConsumerMessageHandler {

    @Autowired
    private CoreConsumerStandard coreConsumerStandard;

    @RabbitListener(queues = Constant.RABBITMQ_QUEUE_NORMAL_NAME + "${spring.application.name}")
    public void handler(Message message){
        log.error("消费者从队列接收此消息{}",message);
        byte[] b = message.getBody();
        log.error("获得消息的body，byte[]为{}",b);
        //把收到的消息转化为XuMessage对象
        XuMessage xuMessage = (XuMessage) SerializationUtils.deserialize(b);
        log.error("消息转化为xuMessage对象{}",xuMessage);
        coreConsumerStandard.sendMessage(xuMessage);
    }
}
