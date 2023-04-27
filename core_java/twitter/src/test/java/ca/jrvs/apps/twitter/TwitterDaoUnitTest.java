package ca.jrvs.apps.twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dto.JsonUtil;
import ca.jrvs.apps.twitter.dto.TweetUtil;
import ca.jrvs.apps.twitter.model.Tweet;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {

  @Mock
  HttpHelper httpHelperMock;

  @InjectMocks
  TwitterDao twitterDao;
  private String STRING_JSON = "{\"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
      + "   \"id\":1650700146370289664,\n"
      + "   \"id_str\":\"1650700146370289664\",\n"
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

  @Test
  public void postTweet() throws Exception {
    String hashtag = "#abc";
    String text = "Hello world" + hashtag + " ";
    when(httpHelperMock.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
    try {
      twitterDao.create(TweetUtil.buildTweet(text));
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
    String tweetJsonString = STRING_JSON;
    when(httpHelperMock.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyTwitterDao = Mockito.spy(twitterDao);
    Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonString, Tweet.class);

    //Mock Parse Response Body
    doReturn(expectedTweet).when(spyTwitterDao).parseResponseBody(any(), anyInt());
    Tweet tweet = spyTwitterDao.create(TweetUtil.buildTweet(text));
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }

  @Test
  public void deleteTweetById() {
    String tweetJson = STRING_JSON;
    TwitterDao spyTwitterDao = Mockito.spy(twitterDao);
    doReturn(null).when(spyTwitterDao).deleteById("1650700146370289664");
    Tweet deletedTweet = spyTwitterDao.deleteById("1650700146370289664");
    assertNull(deletedTweet);
  }

  @Test
  public void findTweetById() {
    String tweetJson = STRING_JSON;
    try {
      twitterDao.findById("1097607853932564480");
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
    TwitterDao spyTwitterDao = Mockito.spy(twitterDao);
    Tweet expectedTweet;
    try {
      expectedTweet = JsonUtil.toObjectFromJson(tweetJson, Tweet.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    doReturn(expectedTweet).when(spyTwitterDao).parseResponseBody(any(), anyInt());
    Tweet actualTweet = spyTwitterDao.findById("1097607853932564480");
    assertEquals(expectedTweet.getId_str(), actualTweet.getId_str());
    assertEquals(expectedTweet.getText(), actualTweet.getText());
  }
}
