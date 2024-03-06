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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

/**
 *
 */
@Entity
@Table(name = "user_profile")
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"key", "created", "modified", "display_name"})
public class User implements UserPublic {

  @Id
  @NonNull
  @GeneratedValue
  @Column(name = "user_profile_id", nullable = false, updatable = false)
  @JsonIgnore
  private Long id;

  @NonNull
  @Column(name = "external_key", nullable = false, updatable = false, unique = true, columnDefinition = "UUID")
  @JsonProperty(access = Access.READ_ONLY)
  private UUID key;

  @NonNull
  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @JsonProperty(access = Access.READ_ONLY)
  private Instant created;

  @NonNull
  @Column(nullable = false)
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @JsonProperty(access = Access.READ_ONLY)
  private Instant modified;

  @NonNull
  @Column(nullable = false, updatable = false, unique = true, length = 50)
  private String displayName;

  @NonNull
  @Column(nullable = false, updatable = false, unique = true, length = 30)
  @JsonIgnore
  private String oauthKey;

  @NonNull
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
      cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("created DESC")
  @JsonIgnore
  private final List<Game> games = new LinkedList<>();

  @ManyToMany(fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinTable(name = "user_following",
      uniqueConstraints = @UniqueConstraint(columnNames = {"followed_id", "follower_id"}),
      joinColumns = @JoinColumn(name = "follower_id", nullable = false),
      inverseJoinColumns = @JoinColumn(name = "followed_id", nullable = false))
  @OrderBy("displayName")
  @JsonIgnore
  private final Set<User> followedUsers = new LinkedHashSet<>();

  @ManyToMany(mappedBy = "followedUsers",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @OrderBy("displayName")
  @JsonIgnore
  private final Set<User> followingUsers = new LinkedHashSet<>();

  /**
   * Returns primary key for the user
   *
   * @return
   */
  @NonNull
  public Long getId() {
    return id;
  }

  @Override
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

  @Override
  @NonNull
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Assigns the specified value to the displayvname of the user
   *
   * @param display_name
   */
  public void setDisplayName(@NonNull String display_name) {
    this.displayName = display_name;
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

  public Set<User> getFollowedUsers() {
    return followedUsers;
  }

  public Set<User> getFollowingUsers() {
    return followingUsers;
  }

  @Override
  public int hashCode() {
    //noinspection ConstantValue
    return (id != null)
        ? id.hashCode()
        : Objects.hash(displayName, oauthKey);
  }

  @SuppressWarnings({"ConstantValue", "SimplifiableConditionalExpression"})
  @Override
  public boolean equals(Object obj) {
    boolean equals;
    if (this == obj) {
      equals = true;
    } else if (obj instanceof User other) {
      equals = (this.id != null && other.id != null)
          ? this.id.equals(other.id)
          : ((this.id == null) != (other.id == null))
              ? false
              : this.displayName.equals(other.displayName) && this.oauthKey.equals(other.oauthKey);
    } else {
      equals = false;
    }
    return equals;
  }


  @PrePersist
  private void generateKey() {
    key = UUID.randomUUID();
  }
}
