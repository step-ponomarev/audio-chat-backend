package edu.chat.demoChat.message.service;

import edu.chat.demoChat.message.Message;

import java.util.List;

public interface MessageService {
  Message sendMessage(String roomId, String guestId, String message);

  List<Message> getMessages(String roomId);
}
