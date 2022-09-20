package com.xx.event.base;

/**
 * @Author: X_X
 * @Date: 2022/9/18 - 09 - 18 - 15:32
 * @Description: com.xx.event
 * @version: 1.0
 */
public interface Constant {
    String RABBITMQ_EXCHANGE_NORMAL_NAME = "event-exchange";

    String RABBITMQ_EXCHANGE_DELAY_NAME = "event-delay-exchange";

    String RABBITMQ_QUEUE_NORMAL_NAME = "event-queue";

}
