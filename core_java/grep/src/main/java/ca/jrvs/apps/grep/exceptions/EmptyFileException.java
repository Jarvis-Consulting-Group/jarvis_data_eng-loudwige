package ca.jrvs.apps.grep.exceptions;

import java.io.IOException;

public class EmptyFileException extends IOException {
  public EmptyFileException(String message){
    super(message);
  }
}
