package com.simple.soap.jms;

import java.io.Serializable;

public class SimpleQueueMessage implements Serializable {

  private String text;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
