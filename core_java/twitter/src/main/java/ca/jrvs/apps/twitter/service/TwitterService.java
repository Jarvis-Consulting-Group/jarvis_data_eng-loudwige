package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.model.Tweet;
import java.io.IOException;
import java.util.List;

public class TwitterService implements Service {
  private TwitterDao twitterDao;
  @Override
  public Tweet postTweet(Tweet tweet) {
    validatePostTweet(tweet);
    return (Tweet) twitterDao.create(tweet);
  }

  private void validatePostTweet(Tweet tweet){
    if(tweet.getText().length() > 140){
      throw new IllegalArgumentException("Tweet text must be shorter than 140");
    }
  }
  @Override
  public Tweet showTweet(String id, String[] fields) {
    return null;
  }

  private void validateTweetId(String id, String[] fields){

  }
  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    return null;
  }
}
