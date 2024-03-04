package edu.cnm.deepdive.codebreaker.model.dao;

import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

  Optional<Game> findGameByKey(UUID key);

  Optional<Game> findGameByKeyAndUser(UUID key, User user);

  // TODO: 2024-02-29 Declare other queries for reporting/ranking/etc.

}
