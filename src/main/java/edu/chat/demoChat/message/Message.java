package edu.chat.demoChat.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Message {
  private String senderName;
  private String roomId;
  private String message;
  private LocalDateTime sendDate;
}
