package com.example.demo.dlx.ttl;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname DlxSender
 * @PackageName com.example.demo.dlx.ttl
 * @Date 2020/1/19 10:36
 */

@ComponentScan(basePackageClasses={DlxSender.class})
public class DlxSender {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DlxSender.class);
		RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);
		RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

		// 随队列的过期属性过期，单位ms
		rabbitTemplate.convertAndSend("GP_ORI_USE_EXCHANGE", "gupao.ori.use", "测试死信消息");

	}
}
