package com.rabbitmq.springdemo;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

/**
 * 消费者
 */
public class Foo3 implements ChannelAwareMessageListener {

    //具体执行业务的方法
    public void listen(String foo) {
        System.out.println("消费者： " + foo);
    }

    @Override
    public void onMessage(Message message, Channel channel) {
            byte[] body = message.getBody();
            System.out.println("foo2"+new String(body));
//            int a = 1/0;
            // 确认收到
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            //ack返回false，并重新回到队列，api里面解释得很清楚
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        //拒绝消息
        //channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
    }
}
