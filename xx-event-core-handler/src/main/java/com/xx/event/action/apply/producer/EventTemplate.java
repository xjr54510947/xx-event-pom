package com.xx.event.action.apply.producer;

import com.xx.event.entity.XuMessage;
import com.xx.event.inter.CoreProducerStandard;
import com.xx.event.utils.ApplicationUtils;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: X_X
 * @Date: 2022/9/18 - 09 - 18 - 11:21
 * @Description: com.xx.event.action.apply.producer
 * @version: 1.0
 */
public class EventTemplate {

    private static CoreProducerStandard coreProducerStandard;

    static {
        coreProducerStandard = ApplicationUtils.getBean(CoreProducerStandard.class);
    }

   public static <T> void sentQuickly(String eventType,T data){
       send(eventType,data,0,null);
   };

    public static <T> void sentDelay(String eventType, T data, long time, TimeUnit unit){
        send(eventType,data,1,unit.toMillis(time));
    };

    public static <T> void sentReliable(String eventType,T data){
        send(eventType,data,2,null);
    };


    private static <T> void send(String eventType, T data, Integer msgType, Long delayTime){
        XuMessage xuMessage = new XuMessage.Builder<T>(eventType,data)
                .setId(UUID.randomUUID().toString())
                .setSendTime(new Date())
                .setMsgType(msgType)
                .setDelayTime(delayTime)
                .build();

        coreProducerStandard.sendMessage(xuMessage);
    }

}
