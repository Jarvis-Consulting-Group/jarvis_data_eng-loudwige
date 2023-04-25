package ca.jrvs.apps.twitter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.dto.JsonUtil;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.dto.TweetUtil;
import org.junit.Before;
import org.junit.Test;

public class TwitterDaoIntTest {
  private TwitterDao twitterDao;
  private HttpHelper httpHelper;

  @Before
  public void setUp() {
    // Create a mock HttpHelper
    httpHelper = new TwitterHttpHelper(
        System.getenv("consumerKey"),
        System.getenv("consumerSecret"),
        System.getenv("accessToken"),
        System.getenv("tokenSecret")
    );

    // Create the TwitterDao instance with the mock HttpHelper
    this.twitterDao = new TwitterDao(httpHelper);
  }

  @Test
  public void create() throws Exception {
    String hashtag = "#abc";
    String text = "Hello EveryBody " + hashtag + " " + System.currentTimeMillis();
    Tweet postTweet = TweetUtil.buildTweet(text);
    //System.out.println(JsonUtil.toPrettyJson(postTweet));

    Tweet tweet = twitterDao.create(postTweet);
    assertEquals(text, tweet.getText());
    assertTrue(hashtag.contains(tweet.getEntities().getHashtags().get(0).getText()));
  }


  @Test
  public void findById() throws Exception {
    String id = "1650685665233842178";
    Tweet tweet = twitterDao.findById(id);
    assertEquals("Hello twitter", tweet.getText());
  }

  @Test
  public void deleteById(){
    String  id = "1650687522039529473";
    twitterDao.deleteById(id);
    assertNull(twitterDao.findById(id));
  }
}
