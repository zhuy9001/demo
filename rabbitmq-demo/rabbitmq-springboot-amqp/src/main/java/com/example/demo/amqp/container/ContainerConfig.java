package com.example.demo.amqp.container;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname ContainerConfig
 * @PackageName com.example.demo.amqp.container
 * @Date 2020/1/17 15:35
 */
@Configuration
public class ContainerConfig {

	@Bean
	public ConnectionFactory connectionFactory() throws Exception{
		CachingConnectionFactory cachingConnectionFactory=new CachingConnectionFactory();
		return cachingConnectionFactory;
	}

	@Bean
	public RabbitAdmin amqpAdmin(ConnectionFactory connectionFactory){
		RabbitAdmin admin=new RabbitAdmin(connectionFactory);
		return admin;
	}

	@Bean("secondQueue")
	public Queue getSecondQueue(){
		return new Queue("GP_BASIC_SECOND_QUEUE");
	}

	@Bean("thirdQueue")
	public Queue getThirdQueue(){
		return new Queue("GP_BASIC_THIRD_QUEUE");
	}

	@Bean
	public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory){
		SimpleMessageListenerContainer container=new SimpleMessageListenerContainer(connectionFactory);
		container.setQueues(getSecondQueue(),getThirdQueue());//监听的队列
		container.setConcurrentConsumers(1);//最小的消费数
		container.setMaxConcurrentConsumers(5);//最大的消费数量
		container.setDefaultRequeueRejected(false);//是否重回队列
		container.setAcknowledgeMode(AcknowledgeMode.AUTO);//签收模式，问题：如何单独设置某个队列为手动签收
		container.setExposeListenerChannel(true);
		container.setConsumerTagStrategy(new ConsumerTagStrategy() {
			@Override
			public String createConsumerTag(String queue) {
				return queue+"_"+ UUID.randomUUID().toString();
			}
		});
		return container;
	}


	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
		SimpleRabbitListenerContainerFactory factory=new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(new Jackson2JsonMessageConverter());
		factory.setAcknowledgeMode(AcknowledgeMode.NONE);
		factory.setAutoStartup(true);
		factory.setConcurrentConsumers(2);
		factory.setMaxConcurrentConsumers(6);
		factory.setTransactionManager(rabbitTransactionManager(connectionFactory));
		return factory;
	}

	public RabbitTransactionManager rabbitTransactionManager(ConnectionFactory connectionFactory) {
		return new RabbitTransactionManager(connectionFactory);
	}
}
