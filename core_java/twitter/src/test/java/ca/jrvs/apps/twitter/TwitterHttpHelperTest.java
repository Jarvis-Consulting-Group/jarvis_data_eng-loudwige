package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import com.google.gdata.util.common.base.PercentEscaper;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TwitterHttpHelperTest {
  String consumerKey = System.getenv("consumerKey");
  String consumerSecret = System.getenv("consumerSecret");
  String accessToken = System.getenv("accessToken");
  String tokenSecret = System.getenv("tokenSecret");
  HttpHelper twitterHttpHelper = new TwitterHttpHelper(consumerKey, consumerSecret,
      accessToken, tokenSecret);
  String id = "1649980136983240705";

  @Test
  public void HttpPost() throws Exception{
    String status = "In case you didn't know!";
    PercentEscaper percentEscaper = new PercentEscaper("", false);
    HttpResponse response = twitterHttpHelper.httpPost(
        URI.create("https://api.twitter.com/1.1/statuses/"
            + "update.json?status=" + percentEscaper.escape(status)));
    System.out.println(EntityUtils.toString(response.getEntity()));
  }

  @Test
  public void HttpGet() throws Exception{
    HttpResponse response1 = twitterHttpHelper.httpGet(URI.create("https://api.twitter.com/1.1/"
        + "statuses/lookup.json?id=" + id));
    System.out.println(EntityUtils.toString(response1.getEntity()));
  }

  @Test
  public void HttpDelete() throws Exception{
    HttpResponse response2  = twitterHttpHelper.httpPost(URI.create("https://api.twitter.com/1.1/"
        + "statuses/destroy/" + id + ".json"));
    System.out.println(EntityUtils.toString(response2.getEntity()));
  }
}
