package edu.cnm.deepdive.codebreaker.model;

import edu.cnm.deepdive.codebreaker.model.entity.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings({"JpaDataSourceORMInspection", "unused"})
@Embeddable
public class RankingId implements Serializable {

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User user;

  private int poolSize;

  private int length;

  public RankingId() {
  }

  public RankingId(User user, int poolSize, int length) {
    this.user = user;
    this.poolSize = poolSize;
    this.length = length;
  }

  public User getUser() {
    return user;
  }

  public int getPoolSize() {
    return poolSize;
  }

  public int getLength() {
    return length;
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, poolSize, length);
  }

  @Override
  public boolean equals(Object obj) {
    boolean equals;
    if(this==obj) {
      equals = true;
    } else if(obj instanceof RankingId other) {
      equals = (this.user.equals(other.user)
          && this.poolSize == other.poolSize
          && this.length == other.length);
    } else {
      equals = false;
    }
    return equals;
  }
}
