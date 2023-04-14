package ca.jrvs.apps.grep.exceptions;

import java.io.IOException;

public class FileReadException extends IOException {
  public FileReadException(String message, Throwable cause){
    super(message, cause);
  }

}
