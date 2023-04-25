package ca.jrvs.apps.twitter.dto;

import ca.jrvs.apps.twitter.model.Tweet;

public class TweetUtil {
  public static Tweet buildTweet(String text){
    Tweet tweet = new Tweet();
    tweet.setText(text);
    return tweet;
  }
}
