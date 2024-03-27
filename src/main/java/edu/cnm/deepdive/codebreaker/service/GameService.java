package edu.cnm.deepdive.codebreaker.service;

import edu.cnm.deepdive.codebreaker.model.dao.GameRepository;
import edu.cnm.deepdive.codebreaker.model.dao.GuessRepository;
import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.model.entity.Guess;
import edu.cnm.deepdive.codebreaker.model.entity.User;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService implements AbstractGameService {

  private final GameRepository gameRepository;
  private final GuessRepository guessRepository;
  private final RandomGenerator rng;

  @Autowired
  public GameService(
      GameRepository gameRepository, GuessRepository guessRepository, RandomGenerator rng) {
    this.gameRepository = gameRepository;
    this.guessRepository = guessRepository;
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
      throw new InvalidPoolException(
          "No unassigned whitespace or control characters allowed in pool");
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
        .map((game) -> {
          validateGuess(guess, game);
          int[] evaluation = evaluate(game.getSecretCode(), guess.getGuessText());
          guess.setCorrect(evaluation[0]);
          guess.setClose(evaluation[1]);
          guess.setGame(game);
          return guessRepository.save(guess);
        })
        .orElseThrow();
  }

  @Override
  public Guess getGuess(UUID gameKey, UUID guessKey, User user) {
    return guessRepository
        .findGuessByGameAndGuessKeysAndUser(gameKey, guessKey, user)
        .orElseThrow();
  }

  private static boolean isValidPoolCodePoint(int codePoint) {
    return Character.isDefined(codePoint)
        && !Character.isWhitespace(codePoint)
        && !Character.isISOControl(codePoint);
  }

  private static void validateGuess(Guess guess, Game game) {
    if (game.isSolved()) {
      throw new GameCompletedException("Secret code has already been guessed");
    }
    if (guess.getGuessText().codePoints().count() != game.getLength()) {
      throw new InvalidGuessException("Guess length must match code length");
    }
    Set<Integer> validCodePoints = game
        .getPool()
        .codePoints()
        .boxed()
        .collect(Collectors.toSet());
    if (guess
        .getGuessText()
        .codePoints()
        .anyMatch((codePoint)-> !validCodePoints.contains(codePoint))) {
      throw new InvalidGuessException("Guess may only contain characters in the pool");
    }
  }

  private int[] evaluate (String code, String guess){
    int[] codeCodePoints = code.codePoints().toArray();
    int[] guessCodePoints = guess.codePoints().toArray();
    Map<Integer, Integer> codeOccurences = new HashMap<>();
    Map<Integer, Integer> guessOccurences = new HashMap<>();
    int correct = 0;
    for(int i = 0; i < codeCodePoints.length; i++) {
      int codeCodePoint = codeCodePoints[i];
      int guessCodePoint = guessCodePoints[i];
      if(codeCodePoint == guessCodePoint){
        correct++;
      } else {
        codeOccurences.put(codeCodePoint, 1 + codeOccurences.getOrDefault(codeCodePoint, 0));
        guessOccurences.put(guessCodePoint, 1 + guessOccurences.getOrDefault(guessCodePoint, 0));
      }
    }
    int close = guessOccurences
        .entrySet()
        .stream()
        .mapToInt((entry)->Math.min(entry.getValue(), codeOccurences.getOrDefault(entry.getKey(), 0)))
        .sum();
    return new int[]{correct, close};
  }

}
