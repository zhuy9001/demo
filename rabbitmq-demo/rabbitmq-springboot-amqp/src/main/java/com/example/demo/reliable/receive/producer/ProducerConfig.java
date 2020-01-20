package com.example.demo.reliable.receive.producer;

import com.example.demo.util.ResourceUtil;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname ProducerConfig
 * @PackageName com.example.demo.reliable.receive.producer
 * @Date 2020/1/19 14:25
 */
@Configuration
public class ProducerConfig {
	@Bean
	public ConnectionFactory connectionFactory() throws Exception {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
		cachingConnectionFactory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
		return cachingConnectionFactory;
	}

	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		return new RabbitTemplate(connectionFactory);
	}
}
