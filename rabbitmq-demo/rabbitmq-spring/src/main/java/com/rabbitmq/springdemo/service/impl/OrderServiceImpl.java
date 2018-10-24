package com.rabbitmq.springdemo.service.impl;

import com.rabbitmq.springdemo.service.OrderService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单业务类
 */
@Service
public class OrderServiceImpl implements OrderService {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        // 添加监听器-Confirm机制的监听
        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) ->
                {
                    if(ack) {
                        // 成功
                    } else {
                        // 失败
                        String id = correlationData.getId();
                        System.out.println(String.format("id=%s, cause=%s", id, cause));
                    }
                }

        );

        // 添加监听器-return机制的监听
        this.rabbitTemplate.setReturnCallback((Message message, int replyCode, String replyText, String exchange, String routingKey)
                -> {
            // 没有找到对应队列的消息
            System.out.println(exchange);
        });
    }

    @Override
    public void pay(String goodsId) {
        rabbitTemplate.convertAndSend("sendGoods", goodsId);
    }

}
