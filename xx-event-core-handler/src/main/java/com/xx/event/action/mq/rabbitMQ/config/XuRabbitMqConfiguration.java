package com.xx.event.action.mq.rabbitMQ.config;

import com.xx.event.action.mq.rabbitMQ.consumer.ConsumerMessageHandler;
import com.xx.event.action.mq.rabbitMQ.producer.ProducerMessageHandler;
import com.xx.event.base.Constant;
import com.xx.event.action.apply.consumer.XuEvent;
import com.xx.event.action.apply.consumer.XuEventHandler;
import com.xx.event.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author: X_X
 * @Date: 2022/9/18 - 09 - 18 - 15:28
 * @Description: com.xx.event.action.mq.rabbitMQ.config
 * @version: 1.0
 */
@Configuration
@Slf4j
public class XuRabbitMqConfiguration {

    /**
     * 消息队列rabbitmq的配置，生产者端配置交换机
     */
    @Configuration
    public static class ProducerConfiguration{
        {
            log.info("生产者端正在创建交换机");
        }
        @Bean
        public ProducerMessageHandler getProducerMessageHandler(){
            return new ProducerMessageHandler();
        }

        @Bean
        public DirectExchange getDirectExchange(){
            return new DirectExchange(Constant.RABBITMQ_EXCHANGE_NORMAL_NAME,true,false);
        }
    }

    /**
     * 消费者端配置队列
     */
    @Configuration
    @ConditionalOnBean(XuEventHandler.class)
    public static class ConsumerConfiguration{

        {
            log.info("消费者端正在创建队列");
        }

        @Value("${spring.application.name}")
        private String queueName;

        @Autowired
        private List<XuEventHandler> xuEventHandlers;


        @Bean
        public ConsumerMessageHandler getConsumerMessageHandler(){
            return new ConsumerMessageHandler();
        }


        @Bean
        public Queue getEventQueue(){
            return new Queue(Constant.RABBITMQ_QUEUE_NORMAL_NAME+queueName,true,false,false);
        }

        /**
         * 然后消费者端还要把队列和交换机进行绑定
         */
        @Bean
        public Binding getBinding(DirectExchange directExchange,Queue queue){
            for (XuEventHandler eventHandler : xuEventHandlers){

               XuEvent xuEvent =  eventHandler.getClass().getAnnotation(XuEvent.class);

               if (xuEvent.value()==null){ throw new RuntimeException("请写上XuEvent的注解名称"); }

               Binding binding =  BindingBuilder.bind(queue).to(directExchange).with(xuEvent.value());

                /**
                 * 这里不能直接返回binding对象，因为有可能有多个绑定需要返回，直接return后面的不能返回了
                 * 我们需要手动的把binding对象放到IOC容器中
                 */
                ApplicationUtils.registerBean(binding.getClass().getName()+binding.hashCode(),binding);
            }
            return null;
        }
    }
}
