package edu.cnm.deepdive.codebreaker.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

@Entity
@Table(
    indexes = {
        @Index(columnList = "user_id, created")
    }
)
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"key", "user", "created", "pool", "length", "guesses", "solved", "secretCode"})
public class Game {
  public static final int MAX_CODE_LENGTH = 12;
  private static final int MAX_POOL_LENGTH = 20;

  @NonNull
  @Id
  @GeneratedValue
  @Column(name = "game_id", nullable = false, updatable = false)
  @JsonIgnore
  private Long id;

  @NonNull
  @Column(name = "external_key", nullable = false, updatable = false, unique = true, columnDefinition = "UUID")
  private UUID key;

  @NonNull
  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @JsonProperty(access = Access.READ_ONLY)
  private Instant created;

  @Column(nullable = false, updatable = false)
  @JsonProperty(access = Access.READ_WRITE)
  @Min(1)
  @Max(MAX_CODE_LENGTH)
  private int length;

  @NonNull
  @Column(nullable = false, updatable = false, length = MAX_POOL_LENGTH)
  @JsonProperty(access = Access.READ_WRITE)
  @Size(max = MAX_POOL_LENGTH)
  private String pool;

  @Column(nullable = false, updatable = false)
  @JsonProperty(access = Access.READ_ONLY)
  private int poolSize;

  @NonNull
  @Column(nullable = false, updatable = false, length = MAX_CODE_LENGTH) // TODO: 2/28/2024 Investigate how database determines length
  @JsonIgnore
  private String secretCode;

  @NonNull
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  @JsonProperty(access = Access.READ_ONLY)
  private User user;

  @NonNull
  @OneToMany(mappedBy = "game", fetch = FetchType.EAGER,
      cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("created ASC")
  @JsonProperty(access = Access.READ_ONLY)
  private final List<Guess> guesses = new LinkedList<>();

  @NonNull
  public Long getId() {
    return id;
  }

  @NonNull
  public UUID getKey() {
    return key;
  }

  @NonNull
  public Instant getCreated() {
    return created;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  @NonNull
  public String getPool() {
    return pool;
  }

  public void setPool(@NonNull String pool) {
    this.pool = pool;
  }

  public int getPoolSize() {
    return poolSize;
  }

  public void setPoolSize(int poolSize) {
    this.poolSize = poolSize;
  }

  @NonNull
  public String getSecretCode() {
    return secretCode;
  }

  public void setSecretCode(@NonNull String secretCode) {
    this.secretCode = secretCode;
  }

  @NonNull
  public User getUser() {
    return user;
  }

  public void setUser(@NonNull User user) {
    this.user = user;
  }

  @NonNull
  public List<Guess> getGuesses() {
    return guesses;
  }

  public boolean isSolved() {
    return guesses
        .stream()
        .anyMatch(Guess::isSolution);
  }

  @JsonProperty("secretCode")
  public String getCode() {
    return isSolved() ? secretCode : null;
  }

  @PrePersist
  private void generateKey() {
    key = UUID.randomUUID();
  }

}
