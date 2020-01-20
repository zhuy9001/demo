package com.example.demo.basic.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname SecondConsumer
 * @PackageName com.example.demo.basic.consumer
 * @Date 2020/1/17 16:35
 */
@Component
@RabbitListener(queues = "GP_BASIC_SECOND_QUEUE")
public class SecondConsumer {

	@RabbitHandler
	public void process(String msg){
		System.out.println(" second queue received msg : " + msg);
	}
}
