package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.dto.JsonUtil;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.springframework.web.util.UriComponentsBuilder;

public class TwitterDao implements CrdDao<Tweet, String> {

  private static final String API_BASE_URI = "https://api.twitter.com";
  private static final String POST_PATH = "/1.1/statuses/update.json?status=";
  private static final String GET_PATH = "/1.1/statuses/show.json";
  private static final String DELETE_PATH = "/1.1/statuses/destroy/";
  private static final String JSON_EXTENSION = ".json";

  private static final String QUERY_SYM = "?";
  private static final int HTTP_OK = 200;
  private final HttpHelper httpHelper;

  public TwitterDao(HttpHelper httpHelper) {
    this.httpHelper = httpHelper;
  }

  @Override
  public Tweet create(Tweet tweet) {
    URI uri;
    try{
      uri = getPostUri(tweet);
    } catch (URISyntaxException | UnsupportedEncodingException e){
      throw new IllegalArgumentException("Invalid tweet input: ", e);
    }
    HttpResponse response = httpHelper.httpPost(uri);
    return parseResponseBody(response, HTTP_OK);
  }

  public Tweet parseResponseBody(HttpResponse response, Integer statusCode) {
    Tweet tweet;
    if (response == null) {
      throw new IllegalArgumentException("Response cannot be null");
    }
    //Check Response Status
    int status = response.getStatusLine().getStatusCode();
    if(status != statusCode){
      try{
        System.out.println(EntityUtils.toString(response.getEntity()));
      }catch(IOException e){
        System.out.println("Response has no entity");
      }
      throw new RuntimeException("Unexpected Http status: " + status);
    }
    //Convert Response Entity to String
    String jsonString;
    try{
      jsonString = EntityUtils.toString(response.getEntity());
    } catch (IOException e){
      throw new RuntimeException("Failed to convert entity to string");
    }

    //Convert Json String to Tweet Object
    try{
      tweet = JsonUtil.toObjectFromJson(jsonString, Tweet.class);
    } catch (IOException e){
      throw new RuntimeException("Unable to convert Json String to object ", e);
    }
    return tweet;
  }

  private static URI getPostUri(Tweet tweet) throws URISyntaxException,
      UnsupportedEncodingException {
    return UriComponentsBuilder.fromUriString(API_BASE_URI + POST_PATH)
        .queryParam("status", URLEncoder.encode(tweet.getText(), "UTF-8"))
        .queryParam("lat", tweet.getCoordinates().getCoordinates().get(0))
        .queryParam("long", tweet.getCoordinates().getCoordinates().get(1))
        .build()
        .toUri();
  }
  private static URI getGetUri(String s) throws URISyntaxException,
      UnsupportedEncodingException {
    return UriComponentsBuilder.fromUriString(API_BASE_URI + GET_PATH + QUERY_SYM)
        .queryParam("id", Long.parseLong(URLEncoder.encode(s, "UTF-8")))
        .build()
        .toUri();
  }
  @Override
  public Tweet findById(String s) {
    URI uri;
    try{
      uri = getGetUri(s);
    } catch (URISyntaxException | UnsupportedEncodingException e){
      throw new IllegalArgumentException("Invalid tweet input: ", e);
    }
    HttpResponse response = httpHelper.httpGet(uri);
    System.out.println("response: " + response);
    return parseResponseBody(response, HTTP_OK);
  }

  @Override
  public Tweet deleteById(String s) {
    URI uri;
    try{
      uri = getDeleteUri(s);
    } catch (URISyntaxException | UnsupportedEncodingException e){
      throw new IllegalArgumentException("Invalid tweet input: ", e);
    }
    HttpResponse response = httpHelper.httpPost(uri);
    if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
      throw new RuntimeException("Unexpected HTTP status: " + response.getStatusLine()
          .getStatusCode());
    }

    // Delete was successful, return null
    return parseResponseBody(response, HTTP_OK);
  }

  private URI getDeleteUri(String s) throws URISyntaxException, UnsupportedEncodingException {
    String encodedText = URLEncoder.encode(s, "UTF-8");
    String uriString = API_BASE_URI + DELETE_PATH + Long.parseLong(encodedText) + JSON_EXTENSION;
    return URI.create(uriString);
  }
}
