package com.simple.soap.jms;

public interface SimpleQueueRequestSender {

  void sendRequestMessageToQueue(SimpleQueueMessage message);
}
