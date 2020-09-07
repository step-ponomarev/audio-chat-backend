package edu.chat.demoChat.room.janus.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.chat.demoChat.room.janus.dto.CreateAudioRoomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class AudioBridgeJanusService implements JanusService {
  private final String HOST = "http://localhost:8088/janus";

  private final HttpClient httpClient;

  @Override
  public void createAudioRoom(CreateAudioRoomDTO createAudioRoomDTO) throws JsonProcessingException {
    httpClient
        .sendAsync(getCreateRoomRequest(createAudioRoomDTO), HttpResponse.BodyHandlers.ofString())
        .thenApply(HttpResponse::body)
        .thenApply(String::toString)
        .thenAccept(System.out::println);
  }

  private HttpRequest getCreateRoomRequest(CreateAudioRoomDTO createAudioRoomDTO) throws JsonProcessingException {
    System.out.println(new ObjectMapper().writeValueAsString(createAudioRoomDTO));
    return HttpRequest.newBuilder()
        .uri(URI.create(HOST))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(createAudioRoomDTO)))
        .build();
  }
}
