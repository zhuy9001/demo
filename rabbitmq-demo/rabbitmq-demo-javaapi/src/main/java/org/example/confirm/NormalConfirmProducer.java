package org.example.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.example.util.ResourceUtil;


/**
 * @author zhuyc
 * @Description TODO 普通确认模式
 * @Classname NormalConfirmProducer
 * @PackageName org.example.confirm
 * @Date 2020/1/17 14:00
 */
public class NormalConfirmProducer {
	private final static String QUEUE_NAME="ORIGIN_QUEUE";

	public static void main(String[] args) throws  Exception{
		ConnectionFactory factory=new ConnectionFactory();
		factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
		Connection connection=factory.newConnection();
		Channel channel=connection.createChannel();
		String msg="Hello world, rabbit mq ,Normal Confirm";
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.confirmSelect();
		channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
		if (channel.waitForConfirms()){
			System.out.println("消息发送成功" );
		}
		channel.close();
		connection.close();
	}
}
