package ca.jrvs.apps.twitter.dto;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;


public class JsonUtil {

  /**
   *
   * @param object An object
   * @param prettyJson set a boolean value for indentation
   * @param includeNullValues should null values be included or not
   * @return a String
   * @throws JsonProcessingException if it couldn't process
   */
  public static String toJson(Object object, boolean prettyJson, boolean includeNullValues)
      throws JsonProcessingException {
    ObjectMapper m = new ObjectMapper();
    if(!includeNullValues){
      m.setSerializationInclusion(Include.NON_NULL);
    }
    if(prettyJson){
      m.enable(SerializationFeature.INDENT_OUTPUT);
    }
    return m.writeValueAsString(object);
  }

  /**
   *
   * @param json a JSon String Object
   * @param clazz The class of the Object to return
   * @return an Object of the specified class
   * @param <T> A generic
   * @throws IOException if it couldn't process
   */

  public static <T> T toObjectFromJson(String json, Class<T> clazz) throws IOException{
    ObjectMapper m = new ObjectMapper();
    return (T) m.readValue(json, clazz);
  }

}
