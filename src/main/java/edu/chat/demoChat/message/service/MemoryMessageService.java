package edu.chat.demoChat.message.service;

import edu.chat.demoChat.message.Message;
import edu.chat.demoChat.message.repository.MessageRepository;
import edu.chat.demoChat.signalingService.SignalingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service("memoryMessageService")
public class MemoryMessageService implements MessageService {
  private final MessageRepository messageRepository;
  private final SignalingService signalingService;

  @Override
  public List<Message> getMessages(String roomId) {
    return messageRepository.findByRoomId(roomId);
  }

  @Override
  public Message sendMessage(String roomId, String senderId, String msg) {
    var message = messageRepository.save(new Message(senderId, roomId, msg, LocalDateTime.now()));

    signalingService.signalMessageSended(message);

    return message;
  }
}
