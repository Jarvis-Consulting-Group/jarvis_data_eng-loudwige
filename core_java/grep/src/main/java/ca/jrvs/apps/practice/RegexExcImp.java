package ca.jrvs.apps.practice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExcImp implements RegexExc {

  /**
   * Pattern for each method
   */
  private static final String PATTERN_JPEG = "\\.jpe?g$";

  private static final String PATTERN_IP = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.)"
      + "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
  private static final String PATTERN_LINE = "^\\s*$";

  /**
   * Check if a filename has .jpeg or .jpg extension
   *
   * @param filename a file name with extension
   * @return boolean
   */
  @Override
  public boolean matchJpeg(String filename) {
    int flag = Pattern.CASE_INSENSITIVE;
    Pattern pattern = Pattern.compile(PATTERN_JPEG, flag);
    Matcher matcher = pattern.matcher(filename);
    return matcher.find();
  }

  /**
   * Check if an IP address is valid
   *
   * @param ip an Ip address
   * @return boolean
   */

  @Override
  public boolean matchIp(String ip) {
    Pattern pattern = Pattern.compile(PATTERN_IP);
    Matcher matcher = pattern.matcher(ip);
    return matcher.matches();
  }

  /**
   * Check if a line is empty
   *
   * @param line a line
   * @return boolean
   */
  @Override
  public boolean isEmptyLine(String line) {
    Pattern pattern = Pattern.compile(PATTERN_LINE);
    Matcher matcher = pattern.matcher(line);
    return matcher.matches();
  }
}
