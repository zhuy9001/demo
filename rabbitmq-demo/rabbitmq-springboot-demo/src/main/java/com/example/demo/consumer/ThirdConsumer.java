package com.example.demo.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname ThirdConsumer
 * @PackageName com.example.demo.consumer
 * @Date 2020/1/19 14:57
 */
@Component
@RabbitListener(queues = "THIRD_QUEUE")
public class ThirdConsumer {
	@RabbitHandler
	public void process(String msg){
		System.out.println(" third queue received msg : " + msg);
	}
}
