package edu.cnm.deepdive.codebreaker.controller;

import edu.cnm.deepdive.codebreaker.service.AbstractRankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RankingController {

  private final AbstractRankingService rankingService;

  @Autowired
  public RankingController(AbstractRankingService rankingService) {
    this.rankingService = rankingService;
  }
}
