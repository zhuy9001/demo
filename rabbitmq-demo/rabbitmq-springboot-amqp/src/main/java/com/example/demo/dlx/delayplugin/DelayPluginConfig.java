package com.example.demo.dlx.delayplugin;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.exception.FatalListenerStartupException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname DelayPluginConfig
 * @PackageName com.example.demo.dlx.delayplugin
 * @Date 2020/1/19 8:55
 */
@Configuration
public class DelayPluginConfig {

	@Bean
	public ConnectionFactory connectionFactory() throws  Exception{
		CachingConnectionFactory cachingConnectionFactory=new CachingConnectionFactory();
		cachingConnectionFactory.setUri("amqp://zhuyc2019:123456@192.168.75.101:5672");
		return cachingConnectionFactory;
	}
	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
		return new RabbitTemplate(connectionFactory);
	}

	@Bean("delayExchange")
	public TopicExchange exchange(){
		Map<String,Object> args=new HashMap<>();
		args.put("x-delayed-type", "direct");
		return new TopicExchange("GP_DELAY_EXCHANGE",true, false,args);
	}

	@Bean("delayQueue")
	public Queue deadLetterQueue(){
		return new Queue("GP_DELAY_QUEUE", true, false, false,new HashMap<>());
	}
	@Bean
	public Binding bindingDead(@Qualifier("delayQueue") Queue queue,@Qualifier("delayExchange") TopicExchange exchange){
		return BindingBuilder.bind(queue).to(exchange).with("#");
	}

}
