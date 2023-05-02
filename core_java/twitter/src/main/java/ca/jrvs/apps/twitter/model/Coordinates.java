package ca.jrvs.apps.twitter.model;

import java.util.ArrayList;

public class Coordinates {
  private ArrayList<Double> coordinates;
  private String type;

  public ArrayList<Double> getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(ArrayList<Double> coordinates) {
    this.coordinates = coordinates;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
