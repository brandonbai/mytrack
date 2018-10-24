package com.rabbitmq.springdemo.controller;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.springdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OrderController {


    @Autowired
    private OrderService orderService;

    @RequestMapping("/pay")
    public String pay() {

        for (int i = 0; i < 100; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i);
            map.put("name", ""+i);
            orderService.pay(JSON.toJSONString(map));

        }

        return "ok";
    }
}
