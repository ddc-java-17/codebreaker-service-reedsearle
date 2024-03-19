package edu.cnm.deepdive.codebreaker.model.entity;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import edu.cnm.deepdive.codebreaker.model.RankingId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Immutable
@Subselect("SELECT * FROM ranking")
public class Ranking {

  @Id
  @JsonUnwrapped
  private RankingId id;

  private int gameCount;

  private double avgGuessCount;

  private double avgDuration;

  public RankingId getId() {
    return id;
  }

  public int getGameCount() {
    return gameCount;
  }

  public double getAvgGuessCount() {
    return avgGuessCount;
  }

  public double getAvgDuration() {
    return avgDuration;
  }

}
