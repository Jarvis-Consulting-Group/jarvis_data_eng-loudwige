package ca.jrvs.apps.twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.dto.JsonUtil;
import ca.jrvs.apps.twitter.dto.TweetUtil;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.Before;
import org.junit.Test;

public class TwitterDaoIntTest {

  private TwitterDao twitterDao;

  @Before
  public void setUp() {

    HttpHelper httpHelper = new TwitterHttpHelper(
        System.getenv("consumerKey"),
        System.getenv("consumerSecret"),
        System.getenv("accessToken"),
        System.getenv("tokenSecret")
    );

    // Create the TwitterDao instance with the mock HttpHelper
    this.twitterDao = new TwitterDao(httpHelper);
  }

  @Test
  public void create() {
    String hashtag = "#abc";
    String text = "Hello EveryBody " + hashtag + " ";
    Double lat = 1d;
    Double lon = -1d;
    Tweet postTweet = TweetUtil.buildTweet(text, lat, lon);

    Tweet tweet = twitterDao.create(postTweet);
    assertEquals(text, tweet.getText());
    assertTrue(hashtag.contains(tweet.getEntities().getHashtags().get(0).getText()));
  }

  @Test
  public void findById() throws Exception {
    String id = "1650695228079087616";
    Tweet tweet = twitterDao.findById(id);
    System.out.println(JsonUtil.toJson(tweet, true, true));
    assertEquals("Hello EveryBody #abc 1682391384642", tweet.getText());
  }

  @Test
  public void deleteById() {
    String id = "1653143409236819972";
    Tweet tweet = twitterDao.deleteById(id);
    assertEquals(tweet.getId_str(), "1653143409236819972");
  }
}
