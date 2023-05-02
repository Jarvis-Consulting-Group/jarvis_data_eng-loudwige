package ca.jrvs.apps.twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dto.JsonUtil;
import ca.jrvs.apps.twitter.dto.TweetUtil;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class TwitterServiceUnitTest {

  private TwitterDao daoMock;
  private TwitterService twitterService;

  @Before
  public void setUp() {
    daoMock = mock(TwitterDao.class);
    twitterService = new TwitterService(daoMock);
  }

  @Test
  public void testCreateTweet() {
    String text = "test tweet";
    Double lat = 1d;
    Double lon = -1d;
    Tweet expectedTweet = TweetUtil.buildTweet(text, lat, lon);
    when(daoMock.create(expectedTweet)).thenReturn(expectedTweet);

    Tweet actualTweet = twitterService.postTweet(expectedTweet);

    assertEquals(expectedTweet.getText(), actualTweet.getText());
    assertEquals(expectedTweet.getCoordinates().getCoordinates().get(0), actualTweet
        .getCoordinates().getCoordinates().get(0));
    assertEquals(expectedTweet.getCoordinates().getCoordinates().get(1), actualTweet
        .getCoordinates().getCoordinates().get(1));
  }

  @Test
  public void testShowTweet() {
    String id = "1653177158645215232";
    String[] fields = {"id_str", "text", "created_at"};
    Tweet expectedTweet;
    try {
      String STRING_JSON = "{\"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
          + "   \"id\":1653177158645215232,\n"
          + "   \"id_str\":\"1653177158645215232\",\n"
          + "   \"text\":\"Hello Twitter!\",\n"
          + "   \"entities\":{\n"
          + "      \"hashtags\":[],      \n"
          + "      \"user_mentions\":[]\n"
          + "   },\n"
          + "   \"coordinates\":null,\n"
          + "   \"retweet_count\":0,\n"
          + "   \"favorite_count\":0,\n"
          + "   \"favorited\":false,\n"
          + "   \"retweeted\":false\n"
          + "}";
      expectedTweet = JsonUtil.toObjectFromJson(STRING_JSON, Tweet.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    when(daoMock.findById(id)).thenReturn(expectedTweet);
    Tweet tweet = twitterService.showTweet(id, fields);
    assertEquals(expectedTweet.getId_str(), tweet.getId_str());
    assertNull(tweet.getCoordinates());
  }

  @Test
  public void testDeleteTweets() {
    String[] tweetIds = {"1234567890", "0987654321"};
    assertEquals(tweetIds.length, twitterService.deleteTweets(tweetIds).size());
  }
}
