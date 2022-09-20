package com.xx.event.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @Author: X_X
 * @Date: 2022/9/18 - 09 - 18 - 11:33
 * @Description: com.xx.event.entity
 * @version: 1.0
 */
@Data
public class XuMessage<T> implements Serializable {

    private XuMessage(){}

    private String id;
//设置发送消息的类型 0-快捷消息 1-延迟消息 2-可靠消息
    private Integer msgType;

    private Long delayTime;

    private Date sendTime;

    private Date receiveTime;

    private String eventType;

    private T data;

    private Map<String,Object> attr;

    public static class Builder<T>{
        private String id;

        private Integer msgType;

        private Long delayTime;

        private Date sendTime;

        private Date receiveTime;

        private String eventType;

        private T data;

        private Map<String,Object> attr;

        public Builder(String eventType,T data){
            this.eventType = eventType;
            this.data = data;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setMsgType(Integer msgType) {
            this.msgType = msgType;
            return this;
        }

        public Builder setDelayTime(Long delayTime) {
            this.delayTime = delayTime;
            return this;
        }

        public Builder setSendTime(Date sendTime) {
            this.sendTime = sendTime;
            return this;
        }

        public Builder setReceiveTime(Date receiveTime) {
            this.receiveTime = receiveTime;
            return this;
        }

        public Builder setAttr(String key,Object value) {
            this.attr.put(key,value);
            return this;
        }

        public XuMessage build(){
            XuMessage xuMessage = new XuMessage();
            xuMessage.setId(id);
            xuMessage.setMsgType(msgType);
            xuMessage.setSendTime(sendTime);
            xuMessage.setDelayTime(delayTime);
            xuMessage.setReceiveTime(receiveTime);
            xuMessage.setEventType(eventType);
            xuMessage.setData(data);
            xuMessage.setAttr(attr);
            return xuMessage;
        }
    }
}
