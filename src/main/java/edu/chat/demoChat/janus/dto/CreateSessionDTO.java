package edu.chat.demoChat.janus.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateSessionDTO {
  private final String janus = "create";
  private String transaction;
}
