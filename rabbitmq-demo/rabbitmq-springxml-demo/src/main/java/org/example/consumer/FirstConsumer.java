package org.example.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname FirstConsumer
 * @PackageName org.example.consumer
 * @Date 2020/1/20 14:12
 */
public class FirstConsumer implements MessageListener {

	private Logger logger = LoggerFactory.getLogger(FirstConsumer.class);
	@Override
	public void onMessage(Message message) {
		logger.info("The first consumer received message : " + message.getBody());
	}
}
