package edu.chat.demoChat.janus.service;

import edu.chat.demoChat.janus.dto.AttachAudioBridgePluginDTO;
import edu.chat.demoChat.janus.dto.CreateAudioRoomDTO;
import edu.chat.demoChat.janus.dto.CreateSessionDTO;

import java.io.IOException;
import java.net.URISyntaxException;

public interface JanusService {
  void createAudioRoom(CreateAudioRoomDTO createAudioRoomDTO, String sessionId, String handleId) throws IOException, InterruptedException, URISyntaxException;

  String createSession(CreateSessionDTO createSessionDTO) throws IOException, InterruptedException;

  String attachAudioBridgePlugin(AttachAudioBridgePluginDTO attachAudioBridgePluginDTO, String sessionId) throws IOException, InterruptedException;
}
