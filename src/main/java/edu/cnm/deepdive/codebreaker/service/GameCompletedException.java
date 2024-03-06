package edu.cnm.deepdive.codebreaker.service;

public class GameCompletedException extends IllegalStateException{


  public GameCompletedException() {
    super();
  }

  public GameCompletedException(String s) {
    super(s);
  }

  public GameCompletedException(String message, Throwable cause) {
    super(message, cause);
  }

  public GameCompletedException(Throwable cause) {
    super(cause);
  }
}
