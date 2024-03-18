package edu.cnm.deepdive.codebreaker.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.cnm.deepdive.codebreaker.model.RankingId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@IdClass(RankingId.class)
@Immutable
@Subselect("SELECT * FROM ranking")
public class Ranking {

  @Id
  @JsonIgnore
  private long userId;

  @Id
  private int poolSize;

  @Id
  private int length;

  private int gameCount;

  private double avgGuessCount;

  private double avgDuration;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "user-id", nullable = false, updatable = false)
  private User user;

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

  @JsonProperty("userId")
  public UUID getExternalKey() {
    return user.getKey();
  }
}
