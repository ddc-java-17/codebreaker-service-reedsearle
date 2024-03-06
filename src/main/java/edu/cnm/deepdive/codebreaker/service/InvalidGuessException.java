package edu.cnm.deepdive.codebreaker.service;

public class InvalidGuessException extends IllegalArgumentException{

  public InvalidGuessException() {
    super();
  }

  public InvalidGuessException(String s) {
    super(s);
  }

  public InvalidGuessException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidGuessException(Throwable cause) {
    super(cause);
  }
}
