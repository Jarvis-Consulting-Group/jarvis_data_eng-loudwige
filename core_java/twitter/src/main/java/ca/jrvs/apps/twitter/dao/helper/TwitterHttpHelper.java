package ca.jrvs.apps.twitter.dao.helper;

import java.io.IOException;
import java.net.URI;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpMethod;

public class TwitterHttpHelper implements HttpHelper {

  private final OAuthConsumer consumer;
  private final HttpClient httpClient;

  public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken,
      String tokenSecret) {
    consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
    consumer.setTokenWithSecret(accessToken, tokenSecret);
    httpClient = HttpClientBuilder.create().build();
  }

  @Override
  public HttpResponse httpPost(URI uri) {
    try{
      return executeHttpRequest(HttpMethod.POST, uri);
    } catch (OAuthException | IOException e) {
      throw new RuntimeException("Failed to execute: ", e);
    }
  }

  @Override
  public HttpResponse httpGet(URI uri) {
    try{
      return executeHttpRequest(HttpMethod.GET, uri);
    } catch (OAuthException | IOException e){
      throw new RuntimeException("Failed to execute: ", e);
    }
  }

  private HttpResponse executeHttpRequest(HttpMethod method, URI uri) throws
      OAuthException, IOException {
    if(method == HttpMethod.GET){
      HttpGet request = new HttpGet(uri);
      consumer.sign(request);
      return httpClient.execute(request);
    } else if(method == HttpMethod.POST){
      HttpPost request = new HttpPost(uri);
      consumer.sign(request);
      return httpClient.execute(request);
    } else {
      throw new IllegalArgumentException("Unknown method: " + method.name());
    }
  }
}
