package edu.chat.demoChat.guest.service;

import edu.chat.demoChat.guest.Guest;

import java.util.List;

public interface GuestService {
  List<Guest> getGuestsWithoutCurrent(String roomId, String sessionId);

  Guest addGuest(String roomId, String sessionId);

  void removeGuest(String sessionId);

  List<Guest> getGuests(String roomId);
}
