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
  private final String HOST = "http://janus-gateway:8088/janus";
  private final ObjectMapper objectMapper;
  private final HttpClient httpClient;

  @Override
  public void createAudioRoom(CreateAudioRoomDTO createAudioRoomDTO, String sessionId, String handleId) throws IOException, InterruptedException {
    var uri = URI.create(HOST + "/" + sessionId + "/" + handleId);
    var request = createRequest(uri, createAudioRoomDTO);

    httpClient.send(request, HttpResponse.BodyHandlers.ofString());
  }

  /**
   * Creates a new session on Janus server
   *
   * @param createSessionDTO
   * @return Id of created session
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  public String createSession(CreateSessionDTO createSessionDTO) throws IOException, InterruptedException {
    var uri = URI.create(HOST);
    var request = createRequest(uri, createSessionDTO);
    var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    return getIdFromResponse(response.body());
  }

  /**
   * Creates new plugin handler for current session
   *
   * @param attachAudioBridgePluginDTO
   * @param sessionId
   * @return Id of created plugin handler
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  public String attachAudioBridgePlugin(AttachAudioBridgePluginDTO attachAudioBridgePluginDTO, String sessionId) throws IOException, InterruptedException {
    var uri = URI.create(HOST + "/" + sessionId);
    var request = createRequest(uri, attachAudioBridgePluginDTO);
    var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    return getIdFromResponse(response.body());
  }

  private HttpRequest createRequest(URI uri, Object body) throws JsonProcessingException {
    return HttpRequest.newBuilder()
        .uri(uri)
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
