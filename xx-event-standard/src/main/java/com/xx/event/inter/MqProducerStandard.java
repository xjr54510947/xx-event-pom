package com.xx.event.inter;

import com.xx.event.entity.XuMessage;

/**
 * @Author: X_X
 * @Date: 2022/9/18 - 09 - 18 - 13:41
 * @Description: com.xx.event.inter
 * @version: 1.0
 */
public interface MqProducerStandard {
    void sendMessage2Mq(XuMessage xuMessage);
}
