package edu.cnm.deepdive.codebreaker.model.dao;

import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.model.entity.Guess;
import edu.cnm.deepdive.codebreaker.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GuessRepository extends JpaRepository<Guess, Long> {

  Optional<Guess> findGuessByKeyAndGame(UUID key, Game game);

  @Query("SELECT gs FROM Guess AS gs "
      + "JOIN gs.game AS gm "
      + "JOIN gm.user "
      + "WHERE gs.key = :guessKey "
      + "AND gm.key = :gameKey "
      + "AND gm.user = :user")
  Optional<Guess> findGuessByGameAndGuessKeysAndUser(UUID gameKey, UUID guessKey, User user);

}
