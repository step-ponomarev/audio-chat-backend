package edu.chat.demoChat.guest.repository;

import edu.chat.demoChat.guest.Guest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends CrudRepository<Guest, String> {
  List<Guest> findAllByRoomId(String roomId);

  Guest findBySessionId(String sessionId);

  void deleteById(String id);
}
