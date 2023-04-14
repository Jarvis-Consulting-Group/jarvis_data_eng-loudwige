package ca.jrvs.apps.practice;

public interface RegexExc {

  /**
   * Return if filename extension is jpg or jpeg (insensitive case)
   *
   * @param filename a file name with extension
   * @return boolean
   */
  boolean matchJpeg(String filename);

  /**
   * Return true if ip is valid Ip address range is from 0.0.0.0 to 999.999.999.999
   *
   * @param ip an Ip address
   * @return boolean
   */
  boolean matchIp(String ip);

  /**
   * Return true if line is empty (e.g. empty, whitespace, tabs, etc)
   *
   * @param line a line
   * @return boolean
   */
  boolean isEmptyLine(String line);
}
