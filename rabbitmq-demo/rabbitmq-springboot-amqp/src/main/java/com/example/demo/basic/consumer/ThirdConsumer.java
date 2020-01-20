package com.example.demo.basic.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname ThirdConsumer
 * @PackageName com.example.demo.basic.consumer
 * @Date 2020/1/17 16:34
 */
@Component
@RabbitListener(queues = "GP_BASIC_THIRD_QUEUE")
public class ThirdConsumer {
	@RabbitHandler
	public void process(String msg){
		System.out.println(" third queue received msg : " + msg);
	}
}
