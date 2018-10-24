package com.rabbitmq.springdemo.service;

/**
 * 订单业务接口
 */
public interface OrderService {

    void pay(String goodsId);
}
