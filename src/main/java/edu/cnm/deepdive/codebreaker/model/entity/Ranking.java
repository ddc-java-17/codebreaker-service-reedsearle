package edu.cnm.deepdive.codebreaker.model.entity;

import edu.cnm.deepdive.codebreaker.model.RankingId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@IdClass(RankingId.class)
@Immutable
@Subselect("SELECT * FROM ranking")
public class Ranking {

  @Id
  private long userId;

  @Id
  private int poolSize;

  @Id
  private int length;

  private int gameCount;

  private double avgGuessCount;

  private double avgDuration;

  public long getUserId() {
    return userId;
  }

  public int getPoolSize() {
    return poolSize;
  }

  public int getLength() {
    return length;
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
