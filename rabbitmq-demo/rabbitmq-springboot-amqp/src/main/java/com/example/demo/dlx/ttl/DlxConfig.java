package com.example.demo.dlx.ttl;

import com.example.demo.util.ResourceUtil;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.MergedAnnotationPredicates;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname DlxConfig
 * @PackageName com.example.demo.dlx.ttl
 * @Date 2020/1/19 10:07
 */
@Configuration
public class DlxConfig {
	@Bean
	public ConnectionFactory connectionFactory() throws  Exception{
		CachingConnectionFactory cachingConnectionFactory=new CachingConnectionFactory();
		cachingConnectionFactory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
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
	@Bean("oriUseQueue")
	public Queue queue(){
		Map<String,Object> map=new HashMap<>();
		map.put("x-message-ttl", 10000); // 10秒钟后成为死信
		map.put("x-detd-letter-exchange", "GP_DEAD_LETTER_EXCHANGE");//队列中的消息变成死信后，进入死信交换机
		return new Queue("GP_ORI_USE_QUEUE", true, false, false,map);
	}
	@Bean("oriUseExchange")
	public DirectExchange exchange(){
		return new DirectExchange("GP_ORI_USE_EXCHANGE",true,false,new HashMap<>());
	}
	@Bean
	public Binding binding(@Qualifier("oriUseQueue") Queue queue,@Qualifier("oriUseExchange")  DirectExchange exchange){
		return BindingBuilder.bind(queue).to(exchange).with("gupao.ori.use");
	}
	@Bean("deatletterExchange")
	public TopicExchange deadLetterExchange(){
		return new TopicExchange("GP_DEAD_LETTER_EXCHANGE",true,false,new HashMap<>());
	}
	@Bean("deatLetterQueue")
	public Queue deadLetterQueue(){
		return new Queue("GP_DEAD_LETTER_QUEUE", true, false, false,new HashMap<>());
	}
	@Bean
	public Binding bindingDead(@Qualifier("deatLetterQueue") Queue queue,@Qualifier("deatletterExchange") TopicExchange exchange){

		return BindingBuilder.bind(queue).to(exchange).with("#");
	}
}
