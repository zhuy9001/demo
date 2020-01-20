package com.example.demo.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname SecondConsumer
 * @PackageName com.example.demo.consumer
 * @Date 2020/1/19 14:56
 */
@Component
@RabbitListener(queues="SECOND_QUEUE")
public class SecondConsumer {

	@RabbitHandler
	public void process(String msg){
		System.out.println(" second queue received msg : " + msg);
	}
}
