## RabbitMQ+Spring示例代码

> 包含两个测试用例

### 1.使用Spring框架以非web项目启动

- 启动类：`SpringMain`
- 配置文件：`classpath:rabbitmq-context.xml`
    - exchange:`fanoutExchange`，类型:`fanout`
    - queue:`myQueue`
    - consumer:`Foo1`

### 2.使用Spring框架以web项目启动

- 主要配置文件：`classpath:applicationContext-rabbitmq.xml`
    - exchange1:`itemDirectExchange`，类型:`direct`
    - queue:`hisQueue`
    - consumer:`Foo3`
    - exchange2:`itemFanoutExchange`，类型:`fanout`
    - queue:`herQueue`
    - consumer:`Foo1`,`Foo2`
- 通过访问`http://localhost:8080/pay`发送消息

### 测试

> 测试过程注意查看rabbitmq web端消息的状态及数量，包括`ready` `unAck`等；

#### 1 测试ack机制

- 在消费者处理代码中抛出异常`int a = 1/0;`，查看rabbitmq web端统计数据的变化。

结果：抛出异常后，消息由`unAck`状态变为`ready`状态。
如果只有一个消费者，会进入`unAck -> 抛出异常 -> ready -> unAck -> 抛出异常`的死循环。

#### 2 测试confirm机制

- 测试前，将配置文件的连接工厂配置加上`publisher-confirms="true"`属性。

- 发送时打上断点，发出前开启断开与服务器的连接，或者使集群不可用，查看现象。

结果：开启了confirm机制，消息没有正常到达服务器端，或在服务器端镜像队列复制及持久化过程中发生异常，会返回消息给生产者。

#### 3 测试return机制

- 测试前，将配置文件的连接工厂配置加上`publisher-returns="true"`属性。
- 设置一个没有绑定队列的交换机，将消息发送到此交换机。

结果：消息没有路由到队列中也就无法持久化，开启了return机制后，消息会重新返回到生产者中。

#### 4 测试服务器单点故障

- 先设置好镜像队列，发送一条消息到一个`broker`节点，将此节点进程杀死。

通过代码查看消息能否从其他节点订阅接收。

结果：消费端通过订阅其他节点会收到消息，但是应该注意连接状态的处理。