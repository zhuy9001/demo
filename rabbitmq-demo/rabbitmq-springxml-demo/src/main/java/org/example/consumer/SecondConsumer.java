package org.example.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname SecondConsumer
 * @PackageName org.example.consumer
 * @Date 2020/1/20 14:09
 */
public class SecondConsumer implements MessageListener {
	private Logger logger=LoggerFactory.getLogger(SecondConsumer.class);

	@Override
	public void onMessage(Message message) {
		logger.info("The second consumer received message : " + message);
	}
}
