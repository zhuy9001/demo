package org.example.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname ThirdConsumer
 * @PackageName org.example.consumer
 * @Date 2020/1/20 14:10
 */
public class ThirdConsumer implements MessageListener {
	private Logger logger=LoggerFactory.getLogger(ThirdConsumer.class);
	@Override
	public void onMessage(Message message) {
		logger.info("The third cosumer received message : " + message);
	}
}
