package edu.chat.demoChat.guest.repository;

import edu.chat.demoChat.guest.Guest;

import java.util.List;

public interface GuestRepository {
  List<Guest> findAll();

  Guest findBySessionId(String sessionId);

  Guest findById(String id);

  List<Guest> findByRoomId(String roomId);

  Guest save(Guest guest);

  void delete(String id);

}
