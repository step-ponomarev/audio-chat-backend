package edu.chat.demoChat.guest.service;

import edu.chat.demoChat.guest.Guest;

import java.util.List;

public interface GuestService {
  List<Guest> getGuests(String roomId);

  List<Guest> getActiveGuests(String roomId);

  Guest createGuest(String roomId);

  void removeGuest(String guestId);

  void registerUser(String sessionId, String guestId, String roomId);

  Guest getGuest(String guestId);
}
