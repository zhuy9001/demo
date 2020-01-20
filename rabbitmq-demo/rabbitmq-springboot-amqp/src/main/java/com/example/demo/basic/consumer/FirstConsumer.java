package com.example.demo.basic.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname FirstConsumer
 * @PackageName com.example.demo.basic.consumer
 * @Date 2020/1/17 16:34
 */
@Component
@RabbitListener(queues = "GP_BASIC_FIRST_QUEUE")
public class FirstConsumer {

	@RabbitHandler
	public void process(String msg, Channel channel, long deliveryTag) throws IOException {
		channel.basicAck(deliveryTag, true);
		System.out.println(" first queue received msg : " + msg);
	}
}
