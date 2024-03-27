package edu.cnm.deepdive.codebreaker.controller;

import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.model.entity.Ranking;
import edu.cnm.deepdive.codebreaker.service.AbstractRankingService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rankings")
public class RankingController {

  private final AbstractRankingService rankingService;

  @Autowired
  public RankingController(AbstractRankingService rankingService) {
    this.rankingService = rankingService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      params = {"pool-size", "code-length"})
  public List<Ranking> get(
      @RequestParam(name = "pool-size") @Min(2) @Max(Game.MAX_POOL_LENGTH) int poolSize,
      @RequestParam(name = "code-length") @Min(2) @Max(Game.MAX_CODE_LENGTH) int codeLength) {
    return rankingService.getWithoutThreshold(poolSize, codeLength);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      params = {"pool-size", "code-length", "games-threshold"})
  public List<Ranking> get(
      @RequestParam("pool-size") @Min(2) @Max(Game.MAX_POOL_LENGTH) int poolSize,
      @RequestParam("code-length") @Min(2) @Max(Game.MAX_CODE_LENGTH) int codeLength,
      @RequestParam("games-threshold") @Min(1) int gamesThreshold) {
    return rankingService.getWithThreshold(poolSize, codeLength, gamesThreshold);
  }


}
