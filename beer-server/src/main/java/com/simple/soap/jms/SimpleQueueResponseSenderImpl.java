package com.simple.soap.jms;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.simple.soap.config.ActiveMqConfig;

@Component
public class SimpleQueueResponseSenderImpl implements SimpleQueueResponseSender {

  private static final Logger log = LoggerFactory.getLogger(SimpleQueueResponseSenderImpl.class);
  
  @Inject
  private JmsTemplate jmsTemplate;
  
  private String messageQueue;
  
  @PostConstruct
  public void initMailQueue() {
    messageQueue = ActiveMqConfig.RESPONSE_QUEUE;
  }
  
  @Override
  public void sendResponseMessageToQueue(SimpleQueueMessage msg) {
    jmsTemplate.send(messageQueue, new MessageCreator(){
      public Message createMessage(Session session) throws JMSException {
        ObjectMessage objectMessage = session.createObjectMessage(msg);
        return objectMessage;
      }
    });
    log.info("Sent email to queue " + messageQueue);
  }

  
}
