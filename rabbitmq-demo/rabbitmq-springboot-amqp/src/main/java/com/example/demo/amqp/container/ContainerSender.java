package com.example.demo.amqp.container;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import java.net.URI;

/**
 * @author zhuyc
 * @Description TODO 需要现在控制台创建队列GP_BASIC_SECOND_QUEUE
 * @Classname ContainerSender
 * @PackageName com.example.demo.amqp.container
 * @Date 2020/1/17 16:06
 */
public class ContainerSender {

	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = new CachingConnectionFactory(new URI("amqp://zhuyc2019:123456@192.168.75.101:5672"));
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		SimpleMessageListenerContainer container = factory.createListenerContainer();
		// 不用工厂模式也可以创建
		// SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setConcurrentConsumers(1);
		container.setQueueNames("GP_BASIC_SECOND_QUEUE");
		container.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				System.out.println("收到消息："+message);
			}
		});
		container.start();

		AmqpTemplate template = new RabbitTemplate(connectionFactory);
		template.convertAndSend("GP_BASIC_SECOND_QUEUE", "msg 1");
		template.convertAndSend("GP_BASIC_SECOND_QUEUE", "msg 2");
		template.convertAndSend("GP_BASIC_SECOND_QUEUE", "msg 3");
	}
}
