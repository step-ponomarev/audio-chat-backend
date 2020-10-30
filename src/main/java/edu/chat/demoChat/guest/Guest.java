package edu.chat.demoChat.guest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Guest {
  @Id
  private String id;
  private String name;
  private String roomId;
  private Boolean active = false;
  @Column(unique = true)
  private String sessionId;

  public Guest(String id, String roomId) {
    this.id = id;
    this.roomId = roomId;
  }
}
