package edu.chat.demoChat.janus.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateAudioRoomBody {
  private String request;
  private String room;
  private boolean is_private;
}
