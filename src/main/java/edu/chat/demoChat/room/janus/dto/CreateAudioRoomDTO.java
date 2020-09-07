package edu.chat.demoChat.room.janus.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class CreateAudioRoomDTO {
  private final String janus = "create";
  private String request;
  private String room;
  private String[] allowed;
  private String transaction;
}
