package edu.chat.demoChat.room.janus.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.chat.demoChat.room.janus.dto.CreateAudioRoomDTO;

public interface JanusService {
  void createAudioRoom(CreateAudioRoomDTO createAudioRoomDTO) throws JsonProcessingException;
}
