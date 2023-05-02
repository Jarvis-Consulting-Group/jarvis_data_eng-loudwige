package ca.jrvs.apps.twitter.dto;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;

public class TweetUtil {

  /**
   * @param text the text to post
   * @param lat  the latitude between -90 and 90
   * @param lon  the longitude between -90 and 90
   * @return a tweet object
   */
  public static Tweet buildTweet(String text, Double lat, Double lon) {
    Tweet tweet = new Tweet();
    tweet.setText(text);
    Coordinates coordinates = new Coordinates();
    ArrayList<Double> coordinate = new ArrayList<>();
    coordinate.add(lat);
    coordinate.add(lon);
    coordinates.setCoordinates(coordinate);
    tweet.setCoordinates(coordinates);
    return tweet;
  }
}
