package edu.chat.demoChat.signalingService;

import edu.chat.demoChat.message.Message;

public interface SignalingService {
  void signalGuestJoinedRoom(String guestId, String roomId);

  void signalToJoinedGuest(String id, String roomId);

  void signalMessageSended(Message message);

  void signalGuestLeavedRoom(String id);
}
