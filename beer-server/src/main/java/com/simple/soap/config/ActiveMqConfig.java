package com.simple.soap.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.simple.soap.jms.SimpleQueueRequestListener;
import com.simple.soap.jms.SimpleQueueRequestSender;
import com.simple.soap.jms.SimpleQueueRequestSenderImpl;

@Configuration
public class ActiveMqConfig {

    public static final String BROKER_URL = "vm://embedded?broker.persistent=false,useShutdownHook=false";
    public static final String REQUEST_QUEUE = "request.queue";
    public static final String RESPONSE_QUEUE = "response.queue";
    
    
    @Bean(name = "jmsConnectionFactory")
    public ConnectionFactory jmsConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setAlwaysSessionAsync(true);
        connectionFactory.setBrokerURL(BROKER_URL);
        return connectionFactory;
    }

    @Bean(name = "jmsTemplate")
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDefaultDestination(new ActiveMQQueue(BROKER_URL));
        jmsTemplate.setConnectionFactory(jmsConnectionFactory());
        return jmsTemplate;
    }

    @Bean(name="requestQueueSender")
    public SimpleQueueRequestSender requestSender() {
        return new SimpleQueueRequestSenderImpl();
    }

    @Bean(name="requestQueueListener")
    public SimpleQueueRequestListener simpleRequestListener() {
        return new SimpleQueueRequestListener();
    }

    @Bean
    public DefaultMessageListenerContainer messageListenerContainer() {
        DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(jmsConnectionFactory());
        messageListenerContainer.setDestinationName(REQUEST_QUEUE);
        messageListenerContainer.setMessageListener(simpleRequestListener());
        return messageListenerContainer;
    }
}
