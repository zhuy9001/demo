package org.example.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname FourthConsumer
 * @PackageName org.example.consumer
 * @Date 2020/1/20 14:11
 */
public class FourthConsumer  implements MessageListener {

	private Logger logger=LoggerFactory.getLogger(FourthConsumer.class);

	@Override
	public void onMessage(Message message) {
		logger.info("The fourth consumer received message : " + message);
	}
}
