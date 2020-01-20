package com.example.demo.reliable.delivery.consumer;

import com.example.demo.util.ResourceUtil;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname ConsumerConfig
 * @PackageName com.example.demo.reliable.delivery.consumer
 * @Date 2020/1/19 10:48
 */
@Configuration
public class ConsumerConfig {
	@Bean
	public ConnectionFactory connectionFactory() throws Exception{
		CachingConnectionFactory cachingConnectionFactory=new CachingConnectionFactory();
		cachingConnectionFactory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
		return cachingConnectionFactory;
	}
	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
		return new RabbitAdmin(connectionFactory);
	}
	@Bean("reliableQueue")
	public Queue queue(){
		return new Queue("GP_RELIABLE_SEND_QUEUE",  true, false, false, new HashMap<>());
	}
	@Bean("directExchange")
	public DirectExchange exchange(){
		return new DirectExchange("GP_RELIABLE_SEND_EXCHANGE", true, false, new HashMap<>());
	}
	@Bean
	public Binding binding(@Qualifier("reliableQueue") Queue queue, @Qualifier("directExchange") DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("gupao.tech");
	}
	public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){
		SimpleMessageListenerContainer messageListenerContainer=new SimpleMessageListenerContainer();
		messageListenerContainer.setConnectionFactory(connectionFactory);
		messageListenerContainer.setConcurrentConsumers(5);
		messageListenerContainer.setMaxConcurrentConsumers(10);
		Map<String,Object> argumentMap=new HashMap<>();
		messageListenerContainer.setConsumerArguments(argumentMap);
		messageListenerContainer.setConsumerTagStrategy(new ConsumerTagStrategy() {
			@Override
			public String createConsumerTag(String queue) {
				return "添加消费者";
			}
		});
		messageListenerContainer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				try {
					System.out.println(new String(message.getBody(),"UTF-8"));
					System.out.println(message.getMessageProperties());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		});
		return messageListenerContainer;
	}
}
