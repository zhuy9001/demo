package com.example.demo.amqp.admin;

import com.example.demo.util.ResourceUtil;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname AmqpConfig
 * @PackageName com.example.demo.amqp.admin
 * @Date 2020/1/17 15:25
 */
@Configuration
public class AmqpConfig {
	@Bean
	public ConnectionFactory connectionFactory() throws Exception {
		CachingConnectionFactory cachingConnectionFactory=new CachingConnectionFactory();
		cachingConnectionFactory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
		return cachingConnectionFactory;
	}

	@Bean
	public RabbitAdmin amqpAdmin(ConnectionFactory connectionFactory) {
		RabbitAdmin admin=new RabbitAdmin(connectionFactory);
		return admin;
	}

	@Bean
	public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container=new SimpleMessageListenerContainer(connectionFactory);
		container.setConsumerTagStrategy(new ConsumerTagStrategy() {
			@Override
			public String createConsumerTag(String queue) {
				return null;
			}
		});
		return container;
	}
}
