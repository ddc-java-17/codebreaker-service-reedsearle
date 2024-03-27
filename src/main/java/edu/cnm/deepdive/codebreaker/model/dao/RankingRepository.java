package edu.cnm.deepdive.codebreaker.model.dao;

import edu.cnm.deepdive.codebreaker.model.RankingId;
import edu.cnm.deepdive.codebreaker.model.entity.Ranking;
import java.util.List;

public interface RankingRepository extends ReadOnlyRepository<Ranking, RankingId> {

  List<Ranking> findAllByIdPoolSizeAndIdLengthOrderByAvgGuessCountAscAvgDurationAsc(int poolSize, int length);

  List<Ranking> findAllByIdPoolSizeAndIdLengthAndGameCountGreaterThanEqualOrderByAvgGuessCountAscAvgDurationAsc(int poolSize, int length, int gameCountThreshold);

}
