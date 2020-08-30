package edu.chat.demoChat.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PeerConnectDTO {
  private String roomId;
  private String userId;
  private String peerId;
}
