package edu.chat.demoChat.janus.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AttachAudioBridgePluginDTO {
  private final String janus = "attach";
  private final String plugin = "edu.chat.demoChat.janus.plugin.audiobridge";
  private String transaction;
}
