package edu.cnm.deepdive.codebreaker.model.entity;

import java.util.UUID;
import org.springframework.lang.NonNull;

public interface UserPublic {

  @NonNull
  UUID getKey();

  @NonNull
  String getDisplayName();
}
