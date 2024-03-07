package edu.cnm.deepdive.codebreaker.service;

import edu.cnm.deepdive.codebreaker.model.entity.Ranking;
import java.util.List;

public interface AbstractRankingService {

  List<Ranking> getWithoutThreshold(int poolSize, int length);

  List<Ranking> getWithThreshold(int poolSize, int length, int gamesThreshold);

}
