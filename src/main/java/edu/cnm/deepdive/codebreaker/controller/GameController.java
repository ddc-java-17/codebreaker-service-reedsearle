package edu.cnm.deepdive.codebreaker.controller;

import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.service.AbstractGameService;
import edu.cnm.deepdive.codebreaker.service.AbstractUserService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class GameController {

  private final AbstractUserService userService;
  private final AbstractGameService gameService;

  public GameController(AbstractUserService userService, AbstractGameService gameService) {
    this.userService = userService;
    this.gameService = gameService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Game> post(@Valid @RequestBody Game game) {
    Game createdGame = gameService.startGame(game, userService.getCurrentUser());
    URI location =
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(getClass()).get(createdGame.getKey()))
            .toUri();
    return ResponseEntity.created(location).body(createdGame);
  }

  @GetMapping(path = "/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Game get(@PathVariable UUID key) {
    return gameService.getGame(key, userService.getCurrentUser());
  }

}
