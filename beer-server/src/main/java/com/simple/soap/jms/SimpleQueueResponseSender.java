package com.simple.soap.jms;

public interface SimpleQueueResponseSender {

  void sendResponseMessageToQueue(SimpleQueueMessage message);
}
