package edu.cnm.deepdive.codebreaker.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class RankingId implements Serializable {

  private long userId;

  private int poolSize;

  private int length;

  public RankingId() {
  }

  public RankingId(long userId, int poolSize, int length) {
    this.userId = userId;
    this.poolSize = poolSize;
    this.length = length;
  }

  public long getUserId() {
    return userId;
  }

  public int getPoolSize() {
    return poolSize;
  }

  public int getLength() {
    return length;
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, poolSize, length);
  }

  @Override
  public boolean equals(Object obj) {
    boolean equals;
    if(this==obj) {
      equals = true;
    } else if(obj instanceof RankingId other) {
      equals = (this.userId == other.userId
          && this.poolSize == other.poolSize
          && this.length == other.length);
    } else {
      equals = false;
    }
    return equals;
  }
}
