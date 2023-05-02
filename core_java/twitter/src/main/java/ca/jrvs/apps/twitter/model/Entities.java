package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Entities {
  private ArrayList<Hashtag> hashtags;

  private UserMention[] userMentions;

  public ArrayList<Hashtag> getHashtags() {
    return hashtags;
  }

  public void setHashtags(ArrayList<Hashtag> hashtags) {
    this.hashtags = hashtags;
  }

  public UserMention[] getUserMentions() {
    return userMentions;
  }

  public void setUserMentions(UserMention[] userMentions) {
    this.userMentions = userMentions;
  }
}
