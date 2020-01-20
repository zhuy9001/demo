package com.example.demo.reliable.receive.consumer;

import com.example.demo.util.ResourceUtil;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname ConsumerConfig
 * @PackageName com.example.demo.reliable.receive.consumer
 * @Date 2020/1/19 14:13
 */
@Configuration
public class ConsumerConfig {

	@Bean
	public ConnectionFactory connectionFactory()throws Exception{
		CachingConnectionFactory cachingConnectionFactory=new CachingConnectionFactory();
		cachingConnectionFactory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
		return cachingConnectionFactory;
	}
	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) throws Exception{
		return new RabbitAdmin(connectionFactory);
	}

	@Bean("gpqueue")
	public Queue queue(){
		return new Queue("GP_RELIABLE_RECEIVE_QUEUE", true, false, false, new HashMap<>());
	}

	@Bean("gpexchange")
	public DirectExchange exchange(){
		return new DirectExchange("GP_RELIABLE_RECEIVE_EXCHANGE", true, false, new HashMap<>());
	}

	@Bean
	public Binding binding(@Qualifier("gpqueue") Queue queue, @Qualifier("gpexchange") DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("gupao.test.reliable");
	}

	@Bean
	public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){
		SimpleMessageListenerContainer messageListenerContainer=new SimpleMessageListenerContainer();
		messageListenerContainer.setConnectionFactory(connectionFactory);
		messageListenerContainer.setQueueNames("GP_RELIABLE_RECEIVE_QUEUE");
		messageListenerContainer.setConcurrentConsumers(5);
		messageListenerContainer.setMaxConcurrentConsumers(10);
		Map<String,Object> argumentMap=new HashMap<>();
		messageListenerContainer.setConsumerArguments(argumentMap);
		messageListenerContainer.setConsumerTagStrategy(new ConsumerTagStrategy() {
			@Override
			public String createConsumerTag(String queue) {
				return "A_PARTICULAR_STRING";
			}
		});
		messageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		messageListenerContainer.setMessageListener(new ChannelAwareMessageListener() {
			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				try {
					System.out.println(new String(message.getBody(), "UTF-8"));
					System.out.println(message.getMessageProperties());
					if ("测试异常".equals(new String(message.getBody(), "UTF-8"))) {
						throw new RuntimeException();
					} else {
						channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
					}
				} catch (RuntimeException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		return messageListenerContainer;
	}

}
