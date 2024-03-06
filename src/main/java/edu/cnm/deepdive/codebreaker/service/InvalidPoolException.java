package edu.cnm.deepdive.codebreaker.service;

public class InvalidPoolException extends IllegalArgumentException{

  public InvalidPoolException() {
    super();
  }

  public InvalidPoolException(String s) {
    super(s);
  }

  public InvalidPoolException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidPoolException(Throwable cause) {
    super(cause);
  }
}
