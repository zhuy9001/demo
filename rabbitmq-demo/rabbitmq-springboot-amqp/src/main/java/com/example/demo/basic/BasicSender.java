package com.example.demo.basic;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname BasicSender
 * @PackageName com.example.demo.basic
 * @Date 2020/1/17 16:36
 */
@ComponentScan(basePackageClasses={BasicSender.class})
public class BasicSender {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BasicSender.class);
		RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);
		RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

		rabbitTemplate.convertAndSend("","GP_BASIC_FIRST_QUEUE","-------- a direct msg");

		rabbitTemplate.convertAndSend("GP_BASIC_TOPIC_EXCHANGE","shanghai.gupao.teacher","-------- a topic msg : shanghai.gupao.teacher");
		rabbitTemplate.convertAndSend("GP_BASIC_TOPIC_EXCHANGE","changsha.gupao.student","-------- a topic msg : changsha.gupao.student");

		rabbitTemplate.convertAndSend("GP_BASIC_FANOUT_EXCHANGE","","-------- a fanout msg");


	}
}
