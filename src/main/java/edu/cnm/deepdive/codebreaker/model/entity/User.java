package edu.cnm.deepdive.codebreaker.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "user_profile")
public class User {

  @Id
  @NonNull
  @GeneratedValue
  @Column(name = "user_profile_id", nullable = false, updatable = false)
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
  @Column(nullable = false)
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Instant modified;

  @NonNull
  @Column(nullable = false, updatable = false, unique = true, length = 50)
  private String display_name;

  @NonNull
  @Column(nullable = false, updatable = false, unique = true, length = 30)
  private String oauthKey;

  @NonNull
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
      cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("created DESC")
  private final List<Game> games = new LinkedList<>();

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
  public Instant getModified() {
    return modified;
  }

  @NonNull
  public String getDisplay_name() {
    return display_name;
  }

  public void setDisplay_name(@NonNull String display_name) {
    this.display_name = display_name;
  }

  @NonNull
  public String getOauthKey() {
    return oauthKey;
  }

  public void setOauthKey(@NonNull String oauthKey) {
    this.oauthKey = oauthKey;
  }

  @NonNull
  public List<Game> getGames() {
    return games;
  }

  @PrePersist
  private void generateKey() {
    key = UUID.randomUUID();
  }
}
