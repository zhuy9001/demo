package com.example.demo.provider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname MyProvider
 * @PackageName com.example.demo.provider
 * @Date 2020/1/19 14:55
 */
@Component
public class MyProvider {
	@Autowired
	AmqpTemplate amqpTemplate;
	public void send(){
		amqpTemplate.convertAndSend("","FIRST_QUEUE","-------- a direct msg");

		amqpTemplate.convertAndSend("TOPIC_EXCHANGE","shanghai.gupao.teacher","-------- a topic msg : shanghai.gupao.teacher");
		amqpTemplate.convertAndSend("TOPIC_EXCHANGE","changsha.gupao.student","-------- a topic msg : changsha.gupao.student");

		amqpTemplate.convertAndSend("FANOUT_EXCHANGE","","-------- a fanout msg");
	}
}
