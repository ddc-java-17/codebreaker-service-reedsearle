package edu.cnm.deepdive.codebreaker.service;

import edu.cnm.deepdive.codebreaker.model.dao.GameRepository;
import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.model.entity.Guess;
import edu.cnm.deepdive.codebreaker.model.entity.User;
import java.util.Arrays;
import java.util.UUID;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService implements AbstractGameService {

  private final GameRepository gameRepository;
  private final RandomGenerator rng;

  @Autowired
  public GameService(GameRepository gameRepository, RandomGenerator rng) {
    this.gameRepository = gameRepository;
    this.rng = rng;
  }

  @Override
  public Game startGame(Game game, User user) {
    int[] pool = game
        .getPool()
        .codePoints()
        .distinct()
        .toArray();
    if (Arrays.stream(pool).anyMatch((codePoint) -> !isValidPoolCodePoint(codePoint))) {
      throw new IllegalArgumentException(); // TODO: 2024-03-01 Make more specific.
    }
    game.setPool(new String(pool, 0, pool.length));
    game.setPoolSize(pool.length);
    int[] secret = IntStream.generate(() -> pool[rng.nextInt(pool.length)])
        .limit(game.getLength())
        .toArray();
    game.setSecretCode(new String(secret, 0, secret.length));
    game.setUser(user);
    return gameRepository.save(game);
  }

  @Override
  public Game getGame(UUID key, User user) {
    return gameRepository
        .findGameByKeyAndUser(key, user)
        .orElseThrow();
  }

  @Override
  public Guess submitGuess(UUID key, Guess guess, User user) {
    return gameRepository
        .findGameByKeyAndUser(key, user)
        .map((game)-> {
          // TODO: 3/4/2024 Validate guess, set additional fields
          guess.setGame(game);
          game.getGuesses().add(guess);
          gameRepository.save(game);
          return guess;
        })
        .orElseThrow();
  }

  @Override
  public Guess getGuess(UUID gameKey, UUID guessKey, User user) {
    throw new UnsupportedOperationException(); // TODO: 2024-03-01 Implement query based on game, guess, & user.
  }

  private static boolean isValidPoolCodePoint(int codePoint) {
    return Character.isDefined(codePoint)
        && !Character.isWhitespace(codePoint)
        && !Character.isISOControl(codePoint);
  }

}
