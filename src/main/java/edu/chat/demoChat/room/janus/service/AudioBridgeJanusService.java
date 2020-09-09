package edu.chat.demoChat.room.janus.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.chat.demoChat.room.janus.dto.AttachAudioBridgePluginDTO;
import edu.chat.demoChat.room.janus.dto.CreateAudioRoomDTO;
import edu.chat.demoChat.room.janus.dto.CreateSessionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AudioBridgeJanusService implements JanusService {
  private final String HOST = "http://localhost:8088/janus";
  private final ObjectMapper objectMapper;
  private final HttpClient httpClient;

  @Override
  public void createAudioRoom(CreateAudioRoomDTO createAudioRoomDTO, String sessionId, String handleId) throws IOException, InterruptedException {
    var response = httpClient
        .send(getCreateRoomRequest(createAudioRoomDTO, sessionId, handleId), HttpResponse.BodyHandlers.ofString());

    System.out.println(response.body());
  }

  @Override
  public String createSession(CreateSessionDTO createSessionDTO) throws IOException, InterruptedException {
    var response = httpClient
        .send(getCreateSessionRequest(createSessionDTO), HttpResponse.BodyHandlers.ofString());

    return getIdFromResponse(response.body());
  }

  @Override
  public String attachAudioBridgePlugin(AttachAudioBridgePluginDTO attachAudioBridgePluginDTO, String sessionId) throws IOException, InterruptedException {
    var response = httpClient
        .send(createAttachAudioBridgePlugin(attachAudioBridgePluginDTO, sessionId), HttpResponse.BodyHandlers.ofString());

    return getIdFromResponse(response.body());
  }

  private HttpRequest getCreateRoomRequest(CreateAudioRoomDTO createAudioRoomDTO, String sessionId, String handleId) throws JsonProcessingException {
    return HttpRequest.newBuilder()
        .uri(URI.create(HOST + "/" + sessionId + "/" + handleId))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(createAudioRoomDTO)))
        .build();
  }

  private HttpRequest getCreateSessionRequest(CreateSessionDTO createSessionDTO) throws JsonProcessingException {
    return HttpRequest.newBuilder()
        .uri(URI.create(HOST))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(createSessionDTO)))
        .build();
  }

  private HttpRequest createAttachAudioBridgePlugin(AttachAudioBridgePluginDTO attachAudioBridgePluginDTO, String sessionId) throws JsonProcessingException {
    return HttpRequest.newBuilder()
        .uri(URI.create(HOST + "/" + sessionId))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(attachAudioBridgePluginDTO)))
        .build();
  }

  private String getIdFromResponse(String response) throws JsonProcessingException {
    HashMap<String, Object> result =
        objectMapper.readValue(response, HashMap.class);
    HashMap idMap =
        objectMapper.readValue(objectMapper.writeValueAsString(result.get("data")), HashMap.class);

    return idMap.get("id").toString();
  }
}
