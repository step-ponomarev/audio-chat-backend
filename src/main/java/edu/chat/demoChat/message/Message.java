package edu.chat.demoChat.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String senderId;
  private String roomId;
  private String message;
  private LocalDateTime sendDate;

  public Message(String senderId, String roomId, String message, LocalDateTime sendDate) {
    this.senderId = senderId;
    this.roomId = roomId;
    this.message = message;
    this.sendDate = sendDate;
  }
}
