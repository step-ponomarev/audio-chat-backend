package edu.chat.demoChat.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Guest {
  private String name;
  private String sessionId;

  public Guest(String sessionId) {
    this.sessionId = sessionId;
  }
}
