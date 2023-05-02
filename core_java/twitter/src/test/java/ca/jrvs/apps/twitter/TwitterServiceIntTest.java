package ca.jrvs.apps.twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.dto.TweetUtil;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TwitterServiceIntTest {

  private TwitterService twitterService;

  @Before
  public void setUp() {

    HttpHelper httpHelper = new TwitterHttpHelper(
        System.getenv("consumerKey"),
        System.getenv("consumerSecret"),
        System.getenv("accessToken"),
        System.getenv("tokenSecret")
    );
    TwitterDao twitterDao = new TwitterDao(httpHelper);
    this.twitterService = new TwitterService(twitterDao);
  }

  @Rule
  public ExpectedException throwException = ExpectedException.none();

  @Test
  public void testTweetLength() {
    String hashtag = "#abc";
    String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor "
        + "incididunt ut labore et dolore magna aliqua. Ut enim ad minim "
        + "veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo "
        + "consequat"
        + hashtag + " ";
    Double lat = 1d;
    Double lon = -1d;
    Tweet postedTweet = TweetUtil.buildTweet(text, lat, lon);
    throwException.expect(IllegalArgumentException.class);
    throwException.expectMessage("Tweet text must be shorter than 140 characters.");
    twitterService.postTweet(postedTweet);
  }

  @Test
  public void testInvalidLatitude() {
    String text = "Hello World!!!!!";
    Double lat = 91d;
    Double lon = -1d;
    Tweet postedTweet = TweetUtil.buildTweet(text, lat, lon);
    throwException.expect(IllegalArgumentException.class);
    throwException.expectMessage("Latitude must be between -90 and 90.");
    twitterService.postTweet(postedTweet);
  }

  @Test
  public void testInvalidLongitude() {
    String text = "Lorem Ipsum";
    Double lat = 1d;
    Double lon = -100d;
    Tweet postedTweet = TweetUtil.buildTweet(text, lat, lon);
    throwException.expect(IllegalArgumentException.class);
    throwException.expectMessage("Longitude must be between -90 and 90.");
    twitterService.postTweet(postedTweet);
  }

  @Test
  public void testShowTweets() {
    String[] fields = {"id_str", "text", "created_at"};
    Tweet tweet = twitterService.showTweet("1653177158645215232", fields);
    assertNull(tweet.getCoordinates());
  }

  @Test
  public void testDeleteTweets() {
    String[] ids = {"1653038062887116801", "1653036636542828546", "1653035061191557120"};
    List<Tweet> deletedTweets = twitterService.deleteTweets(ids);
    assertEquals(ids.length, deletedTweets.size());

    for (Tweet tweet : deletedTweets) {
      assertTrue(Arrays.asList(ids).contains(tweet.getId_str()));
    }
  }
}
