package org.example.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname MyProducer
 * @PackageName org.example.simple
 * @Date 2020/1/17 15:00
 */
public class MyProducer {

	private final static String EXCHANGE_NAME = "SIMPLE_EXCHANGE";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		// 连接IP
		factory.setHost("192.168.75.101");
		// 连接端口
		factory.setPort(5672);
		// 虚拟机
		factory.setVirtualHost("/");
		// 用户
		factory.setUsername("zhuyc2019");
		factory.setPassword("123456");

		// 建立连接
		Connection conn = factory.newConnection();
		// 创建消息通道
		Channel channel = conn.createChannel();

		// 发送消息
		String msg = "Hello world, Rabbit MQ";

		// String exchange, String routingKey, BasicProperties props, byte[] body
		channel.basicPublish(EXCHANGE_NAME, "gupao.best", null, msg.getBytes());

		channel.close();
		conn.close();
	}
}
