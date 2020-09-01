package edu.chat.demoChat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Message {
  private String senderName;
  private String message;
  private LocalDateTime sendDate;
}
