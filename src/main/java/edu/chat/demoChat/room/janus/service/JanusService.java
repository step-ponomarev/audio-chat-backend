package edu.chat.demoChat.room.janus.service;

import edu.chat.demoChat.room.janus.dto.AttachAudioBridgePluginDTO;
import edu.chat.demoChat.room.janus.dto.CreateAudioRoomDTO;
import edu.chat.demoChat.room.janus.dto.CreateSessionDTO;

import java.io.IOException;

public interface JanusService {
  void createAudioRoom(CreateAudioRoomDTO createAudioRoomDTO, String sessionId, String handleId) throws IOException, InterruptedException;

  String createSession(CreateSessionDTO createSessionDTO) throws IOException, InterruptedException;

  String attachAudioBridgePlugin(AttachAudioBridgePluginDTO attachAudioBridgePluginDTO, String sessionId) throws IOException, InterruptedException;
}
