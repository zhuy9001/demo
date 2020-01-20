package com.example.demo.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname FirstConsumer
 * @PackageName com.example.demo.consumer
 * @Date 2020/1/19 14:56
 */
@Component
@RabbitListener(queues="FIRST_QUEUE")
public class FirstConsumer {
	@RabbitHandler
	public void process(String msg){
		System.out.println(" first queue received msg : " + msg);
	}
}
