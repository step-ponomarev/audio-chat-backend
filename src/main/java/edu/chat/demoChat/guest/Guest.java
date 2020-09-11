package edu.chat.demoChat.guest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Guest {
  private String id;
  private String name;
  private String roomId;
  private Boolean active;
  private String SessionId;

  public Guest(String id, String roomId) {
    this.id = id;
    this.roomId = roomId;
    this.active = false;
  }
}
