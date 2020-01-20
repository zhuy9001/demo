package org.example.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.example.util.ResourceUtil;

import java.io.IOException;

/**
 * @author zhuyc
 * @Description TODO 消息生产者，测试Confirm模式
 * @Classname BatchConfirmProducer
 * @PackageName org.example.confirm
 * @Date 2020/1/17 14:06
 */
public class BatchConfirmProducer {
	private final static String QUEUE_NAME="ORIGIN_QUEUE";

	public static void main(String[] args) throws  Exception {
		ConnectionFactory factory=new ConnectionFactory();
		factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
		Connection connection=factory.newConnection();
		Channel channel=connection.createChannel();
		String msg="Hello world, rabbit mq ,Batch Confirm";
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		try {
			channel.confirmSelect();
			for (int i=0;i<5;i++){
				// 发送消息
				channel.basicPublish("", QUEUE_NAME, null, (msg+"-"+i).getBytes());
			}
			// 批量确认结果，ACK如果是Multiple=True，代表ACK里面的Delivery-Tag之前的消息都被确认了
			// 比如5条消息可能只收到1个ACK，也可能收到2个（抓包才看得到）
			// 直到所有信息都发布，只要有一个未被Broker确认就会IOException
			channel.waitForConfirmsOrDie();
			System.out.println("消息发送完毕，批量确认成功");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		channel.close();
		connection.close();
	}
}
