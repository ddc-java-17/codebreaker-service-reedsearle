package edu.cnm.deepdive.codebreaker.service;

import edu.cnm.deepdive.codebreaker.model.entity.Ranking;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RankingService implements AbstractRankingService {

  @Override
  public List<Ranking> getWithoutThreshold(int poolSize, int length) {
    return null;
  }

  @Override
  public List<Ranking> getWithThreshold(int poolSize, int length, int gamesThreshold) {
    return null;
  }
}
