package edu.chat.demoChat.janus.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.chat.demoChat.janus.dto.AttachAudioBridgePluginDTO;
import edu.chat.demoChat.janus.dto.CreateAudioRoomDTO;
import edu.chat.demoChat.janus.dto.CreateSessionDTO;
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
    var url = HOST + "/" + sessionId + "/" + handleId;
    var request = createRequest(url, createAudioRoomDTO);

    httpClient.send(request, HttpResponse.BodyHandlers.ofString());
  }

  @Override
  public String createSession(CreateSessionDTO createSessionDTO) throws IOException, InterruptedException {
    var url = HOST;
    var request = createRequest(url, createSessionDTO);
    var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    return getIdFromResponse(response.body());
  }

  @Override
  public String attachAudioBridgePlugin(AttachAudioBridgePluginDTO attachAudioBridgePluginDTO, String sessionId) throws IOException, InterruptedException {
    var url = HOST + "/" + sessionId;
    var request = createRequest(url, attachAudioBridgePluginDTO);
    var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    return getIdFromResponse(response.body());
  }

  private HttpRequest createRequest(String url, Object body) throws JsonProcessingException {
    return HttpRequest.newBuilder()
        .uri(URI.create(url))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(body)))
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
