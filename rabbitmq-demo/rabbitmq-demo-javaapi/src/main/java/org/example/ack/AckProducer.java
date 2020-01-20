package org.example.ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.example.util.ResourceUtil;

/**
 * @author zhuyc
 * @Description 消息生产者，用于测试消费者手工应答和重回队列
 * @Classname AckProducer
 * @PackageName org.example.ack
 * @Date 2020/1/17 10:59
 */
public class AckProducer {
	private final static String QUEUE_NAME="TEST_ACK_QUEUE";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory=new ConnectionFactory();
		factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));

		Connection connection=factory.newConnection();
		Channel channel=connection.createChannel();
		String msg="test ack message";
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		for(int i=0;i<5;i++){
			channel.basicPublish("", QUEUE_NAME, null, (msg+i).getBytes());
		}
		channel.close();
		connection.close();

	}
}
