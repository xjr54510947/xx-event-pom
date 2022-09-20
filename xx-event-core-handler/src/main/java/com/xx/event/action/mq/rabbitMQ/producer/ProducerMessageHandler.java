package com.xx.event.action.mq.rabbitMQ.producer;

import com.xx.event.base.Constant;
import com.xx.event.entity.XuMessage;
import com.xx.event.inter.MqProducerStandard;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: X_X
 * @Date: 2022/9/18 - 09 - 18 - 17:09
 * @Description: com.xx.event.action.mq.rabbitMQ.producer
 * @version: 1.0
 */
public class ProducerMessageHandler implements MqProducerStandard {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage2Mq(XuMessage xuMessage) {

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(xuMessage.getMsgType() == 0? MessageDeliveryMode.NON_PERSISTENT:MessageDeliveryMode.PERSISTENT);

        //判断接接收到的消息是否为延迟消息
        if (xuMessage.getMsgType() == 1){
            messageProperties.setHeader("x-delay",xuMessage.getDelayTime());
        }
        byte[] body = SerializationUtils.serialize(xuMessage);
        //封装成mq的消息对象
        Message message = new Message(body,messageProperties);

        if (xuMessage.getMsgType()==2){
            //进行kafka的可靠消息
        }else if (xuMessage.getMsgType() == 0){
            //把消息发送到交换机
            rabbitTemplate.send(Constant.RABBITMQ_EXCHANGE_NORMAL_NAME,xuMessage.getEventType(),message);
        }else {
            rabbitTemplate.send(Constant.RABBITMQ_EXCHANGE_DELAY_NAME,xuMessage.getEventType(),message);
        }



    }
}
