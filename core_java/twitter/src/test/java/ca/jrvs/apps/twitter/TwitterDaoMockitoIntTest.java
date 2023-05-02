package ca.jrvs.apps.twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dto.TweetUtil;
import ca.jrvs.apps.twitter.model.Tweet;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoMockitoIntTest {

  private TwitterDao twitterDao;
  private String text;
  private String tweetId;
  private final String hashtag = "#abc";

  @Before
  public void setUp() throws UnsupportedEncodingException {
    HttpHelper httpHelper = Mockito.mock(HttpHelper.class);
    this.twitterDao = new TwitterDao(httpHelper);
    tweetId = String.valueOf(System.currentTimeMillis());
    text = "Hello Hi, How is it going! " + hashtag + " " + tweetId;
    HttpResponse response = new BasicHttpResponse(
        new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, ""));
    response.setEntity(new StringEntity(
        "{\"created_at\":\"Mon Apr 26 23:11:23 +0000 2021\",\"id\":" + tweetId + ",\"id_str\":\""
            + tweetId + "\",\"text\":\""
            + text + "\",\"entities\":{\"hashtags\":[{\"text\":\"" + hashtag
            + "\",\"indices\":[31,35]}]}}"));
    Mockito.when(httpHelper.httpPost(Mockito.any())).thenReturn(response);
    Mockito.when(httpHelper.httpGet(Mockito.any())).thenReturn(response);
  }

  @Test
  public void createTweet() {
    Double lon = -1d;
    Double lat = 1d;
    Tweet tweetToPass = TweetUtil.buildTweet(text, lat, lon);
    Tweet receivedTweet = twitterDao.create(tweetToPass);
    assertEquals(text, receivedTweet.getText());
  }

  @Test
  public void findTweetByIdTest() {
    Tweet receivedTweet = twitterDao.findById(tweetId);
    assertEquals(text, receivedTweet.getText());
    assertTrue(hashtag.contains(receivedTweet.getEntities().getHashtags().get(0).getText()));
  }

  @Test
  public void deleteTweetById() {
    Tweet deletedTweet = twitterDao.deleteById(tweetId);
    assertEquals(deletedTweet.getId_str(), tweetId);
  }
}
