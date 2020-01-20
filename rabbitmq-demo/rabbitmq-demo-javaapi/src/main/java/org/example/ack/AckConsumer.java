package org.example.ack;

import com.rabbitmq.client.*;
import javafx.css.FontCssMetaData;
import org.example.util.ResourceUtil;

import java.io.IOException;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname AckConsumer
 * @PackageName org.example.ack
 * @Date 2020/1/17 11:06
 */
public class AckConsumer {
	private final static String QUEUE_NAME="TEST_ACK_QUEUE";

	public static void main(String[] args) throws  Exception {
		ConnectionFactory factory=new ConnectionFactory();
		factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
		Connection connection=factory.newConnection();
		final Channel channel=connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println("waiting for message...");
		Consumer consumer=new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String msg =new String(body,"UTF-8");
				System.out.println("Received message : '" + msg + "'");
				if(msg.contains("拒收")){
					// 拒绝消息
					// requeue：是否重新入队列，true：是；false：直接丢弃，相当于告诉队列可以直接删除掉
					// TODO 如果只有这一个消费者，requeue 为true 的时候会造成消息重复消费
					channel.basicReject(envelope.getDeliveryTag(), false);
				}else if(msg.contains("异常")){
					// 批量拒绝
					// requeue：是否重新入队列
					// TODO 如果只有这一个消费者，requeue 为true 的时候会造成消息重复消费
					channel.basicNack(envelope.getDeliveryTag(), true, false);
				} else {
					// 手工应答
					// 如果不应答，队列中的消息会一直存在，重新连接的时候会重复消费
					channel.basicAck(envelope.getDeliveryTag(), true);
				}
			}
		};
		// 开始获取消息，注意这里开启了手工应答
		// String queue, boolean autoAck, Consumer callback
		channel.basicConsume(QUEUE_NAME, false, consumer);

	}
}
