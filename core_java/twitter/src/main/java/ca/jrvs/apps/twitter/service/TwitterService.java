package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwitterService implements Service {
  private final TwitterDao twitterDao;

  public TwitterService(TwitterDao twitterDao) {
    this.twitterDao = twitterDao;
  }


  @Override
  public Tweet postTweet(Tweet tweet) {
    validatePostTweet(tweet);
    return twitterDao.create(tweet);
  }

  private void validatePostTweet(Tweet tweet){
    if (tweet.getText().length() > 140) {
      throw new IllegalArgumentException("Tweet text must be shorter than 140 characters.");
    }
    Coordinates coordinates = tweet.getCoordinates();
    ArrayList<Double> coords = coordinates.getCoordinates();
    Double latitude = coords.get(0);
    if (latitude < -90 || latitude > 90) {
      throw new IllegalArgumentException("Latitude must be between -90 and 90.");
    }
    Double longitude = coords.get(1);
    if (longitude < -90 || longitude > 90) {
      throw new IllegalArgumentException("Longitude must be between -90 and 90.");
    }
  }

  @Override
  public Tweet showTweet(String id, String[] fields) {
    validateTweetId(id);
    Tweet request = twitterDao.findById(id);
    Field[] fields1 = request.getClass().getDeclaredFields();
    for(Field field : fields1){
      String fieldName = field.getName();
      if(!Arrays.asList(fields).contains(fieldName)){
        try {
          field.setAccessible(true);
          if(field.getType() == int.class || field.getType() == boolean.class) {
            // Skip fields that are of int and boolean type
            continue;
          }
          field.set(request, null);
        } catch (IllegalAccessException e){
          System.err.println("Error setting field " + fieldName + " to null: " + e.getMessage());
        }
      }
    }
    return request;
  }

  private void validateTweetId(String id){
    if(id == null || id.isEmpty() || !id.matches("\\d+")){
      throw new RuntimeException("Id must be a 64 bit integer.");
    }
  }
  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    List<Tweet> deletedTweets = new ArrayList<>();
    Arrays.stream(ids).forEach(this::validateTweetId);
    Arrays.stream(ids).forEach(id ->{
      Tweet deletedTweet = twitterDao.deleteById(id);
      deletedTweets.add(deletedTweet);
    });
    return deletedTweets;
  }
}
