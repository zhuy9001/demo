package com.example.demo.reliable.receive.consumer;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname ConsumerApp
 * @PackageName com.example.demo.reliable.receive.consumer
 * @Date 2020/1/19 14:24
 */
@ComponentScan(basePackageClasses=ConsumerApp.class)
public class ConsumerApp {
	public static void main(String[] args) {
		new AnnotationConfigApplicationContext(ConsumerApp.class);
	}
}
