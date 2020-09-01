package edu.chat.demoChat.signaler;

import edu.chat.demoChat.message.Message;

public interface SignalingService {
  void signalGuestJoinedRoom(String guestId, String roomId);

  void signalToJoinedGuest(String id, String roomId);

  void signalGuestLeavedRoom(String id);

  void signalMessageSended(Message message);
}
