package com.simple.soap.repository;

import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.simple.soap.config.ActiveMqConfig;
import com.simple.soap.jms.SimpleQueueMessage;
import com.simple.soap.jms.SimpleQueueRequestSender;
import com.soap.simple.beer.Beer;

@Repository
public class BeerRepositoryImpl implements BeerRepository {

  private static final Logger log = LoggerFactory.getLogger(BeerRepositoryImpl.class);

  @Inject
  private SimpleQueueRequestSender queueSender;
  
  @Override
  public Beer getBeerById(Integer id) {
    Beer beer = new Beer();
    beer.setId(id);
    
    SimpleQueueMessage queueMessage = new SimpleQueueMessage();
    queueMessage.setText(String.valueOf(id));
    queueSender.sendRequestMessageToQueue(queueMessage);
    
    try {
      ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMqConfig.BROKER_URL);
      Connection connection = connectionFactory.createConnection();
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      connection.start();
      Destination destination = session.createQueue(ActiveMqConfig.RESPONSE_QUEUE);
      MessageConsumer consumer = session.createConsumer(destination);
      // By default this call is blocking, which means it will wait
      // for a message to arrive on the queue.
      Message message = consumer.receive();
      if (message instanceof ObjectMessage) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        SimpleQueueMessage requestMessage = (SimpleQueueMessage) objectMessage.getObject();
        String text = requestMessage.getText();
        beer.setName(text);
      }
    } catch (JMSException jmsException) {
      log.error(jmsException.getMessage());
    }
    return beer;
  }

}
