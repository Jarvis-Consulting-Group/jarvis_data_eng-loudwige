package ca.jrvs.apps.grep.exceptions;

import java.io.IOException;

public class ListFilesException extends IOException {
  public ListFilesException(String message){
    super(message);
  }
}
