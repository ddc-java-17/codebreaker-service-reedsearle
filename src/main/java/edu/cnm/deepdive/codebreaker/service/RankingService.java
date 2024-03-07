package edu.cnm.deepdive.codebreaker.service;

import edu.cnm.deepdive.codebreaker.model.dao.RankingRepository;
import edu.cnm.deepdive.codebreaker.model.entity.Ranking;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankingService implements AbstractRankingService {

  private final RankingRepository rankingRepository;

  @Autowired
  public RankingService(RankingRepository rankingRepository) {
    this.rankingRepository = rankingRepository;
  }

  @Override
  public List<Ranking> getWithoutThreshold(int poolSize, int length) {
    return rankingRepository
        .findAllByPoolSizeAndLengthOrderByAvgGuessCountAscAvgDurationAsc(poolSize, length);
  }

  @Override
  public List<Ranking> getWithThreshold(int poolSize, int length, int gamesThreshold) {
    return rankingRepository
        .findAllByPoolSizeAndLengthAndGameCountGreaterThanEqualOrderByAvgGuessCountAscAvgDurationAsc(poolSize, length, gamesThreshold);
  }
}
