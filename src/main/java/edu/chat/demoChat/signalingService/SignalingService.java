package edu.chat.demoChat.signalingService;

import edu.chat.demoChat.guest.Guest;
import edu.chat.demoChat.message.Message;

public interface SignalingService {
  void signalGuestJoinedRoom(Guest guest);

  void signalMessageSended(Message message);

  void signalGuestLeavedRoom(String guestId);
}
