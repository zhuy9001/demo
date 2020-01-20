package com.example.demo.amqp.template;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname TemplateSender
 * @PackageName com.example.demo.amqp.template
 * @Date 2020/1/17 16:13
 */
@ComponentScan(basePackageClasses={TemplateSender.class})
public class TemplateSender {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TemplateSender.class);
		RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

//		rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback(){
//			public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//				if (ack) {
//					System.out.println("消息确认成功");
//				} else {
//					// nack
//					System.out.println("消息确认失败");
//				}
//			}
//		});

		rabbitTemplate.convertAndSend("GP_BASIC_FANOUT_EXCHANGE", "", "this is a msg");
	}
}
