package com.simple.soap.jms;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// This class not annotated with @Component because it's already added as a @Bean in ActiveMqConfig.java
public class SimpleQueueRequestListener implements MessageListener {

  private static final Logger log = LoggerFactory.getLogger(SimpleQueueRequestListener.class);
  
  @Inject
  private SimpleQueueResponseSender responseQueueSender;
  
  @Override
  public void onMessage(Message message) {
    try {
      log.info("Received message from queue");
      if (message instanceof ObjectMessage) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        SimpleQueueMessage requestMessage = (SimpleQueueMessage) objectMessage.getObject();
        String text = requestMessage.getText();
        if ("1".equals(text)) {
          log.info("Found ");
          SimpleQueueMessage responseMessage = new SimpleQueueMessage();
          responseMessage.setText("Kronenbourg");
          responseQueueSender.sendResponseMessageToQueue(responseMessage);
        }
        log.info("Message sent");
      }
    } catch (JMSException e) {
      log.error(e.getMessage());
    } catch (NullPointerException e) {
      log.error(e.getMessage());
    }
  }

}
