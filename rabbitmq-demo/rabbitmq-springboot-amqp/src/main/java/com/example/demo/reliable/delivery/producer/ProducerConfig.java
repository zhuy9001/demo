package com.example.demo.reliable.delivery.producer;

import com.example.demo.util.ResourceUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname ProducerConfig
 * @PackageName com.example.demo.reliable.delivery.producer
 * @Date 2020/1/19 11:21
 */
@Configuration
public class ProducerConfig {

	@Bean
	public ConnectionFactory connectionFactory() throws Exception{
		CachingConnectionFactory cachingConnectionFactory=new CachingConnectionFactory();
		cachingConnectionFactory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
		//cachingConnectionFactory.setPublisherConfirms(true);
		cachingConnectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
		cachingConnectionFactory.setPublisherReturns(true);
		return cachingConnectionFactory;
	}

	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
		RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMandatory(true);
		rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
			@Override
			public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
				try {
					System.out.println("--------收到无法路由回发的消息--------");
					System.out.println("replyCode:" + replyCode);
					System.out.println("replyText:" + replyText);
					System.out.println("exchange:" + exchange);
					System.out.println("routingKey:" + routingKey);
					System.out.println("properties:" + message.getMessageProperties());
					System.out.println("body:" + new String(message.getBody(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		});
		rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
			@Override
			public void confirm(CorrelationData correlationData, boolean ack, String cause) {
				System.out.println("--------收到服务端异步确认--------");
				System.out.println("received ack: "+ack);
				System.out.println("cause: "+cause);
				System.out.println("correlationId: "+correlationData.getId());
			}
		});
		return rabbitTemplate;
	}
}
