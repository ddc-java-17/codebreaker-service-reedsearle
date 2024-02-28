package edu.cnm.deepdive.codebreaker.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

@Entity
@Table(
    indexes = {
        @Index(columnList = "game_id, created")
    }
)
public class Guess {
  public static final int MAX_CODE_LENGTH = 12;


  @NonNull
  @Id
  @GeneratedValue
  @Column(name = "guess_id", nullable = false, updatable = false)
  private Long id;

  @NonNull
  @Column(name = "external_key", nullable = false, updatable = false, unique = true, columnDefinition = "UUID")
  private UUID key;

  @NonNull
  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Instant created;

  @NonNull
  @Column(nullable = false, updatable = false, length = MAX_CODE_LENGTH)
  private String guessText;

  @Column(nullable = false, updatable = false)
  private int correct;

  @Column(nullable = false, updatable = false)
  private int close;

  @NonNull
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "game_id", nullable = false, updatable = false)
  private Game game;

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

  @NonNull
  public String getGuessText() {
    return guessText;
  }

  public void setGuessText(@NonNull String guessText) {
    this.guessText = guessText;
  }

  public int getCorrect() {
    return correct;
  }

  public void setCorrect(int correct) {
    this.correct = correct;
  }

  public int getClose() {
    return close;
  }

  public void setClose(int close) {
    this.close = close;
  }

  @NonNull
  public Game getGame() {
    return game;
  }

  public void setGame(@NonNull Game game) {
    this.game = game;
  }

  @PrePersist
  private void generateKey() {
    key = UUID.randomUUID();
  }
}
