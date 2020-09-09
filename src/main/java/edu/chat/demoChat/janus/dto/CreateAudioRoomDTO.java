package edu.chat.demoChat.janus.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class CreateAudioRoomDTO {
  private final String janus = "message";
  private final String error = null;
  private String transaction;
  private CreateAudioRoomBody body;
}
