package ca.jrvs.apps.practice;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Test class for the RegexExcImp class methods
 */
public class RegexExcImpTests {

  private static RegexExcImp regexExcImp;

  @BeforeAll
  static void initialize() {
    regexExcImp = new RegexExcImp();
  }

  /**
   * Test matchJpeg method
   */
  @Test
  void matchJpegTest() {
    assertTrue(regexExcImp.matchJpeg("filename.jpeg"));
    assertTrue(regexExcImp.matchJpeg("filename.jpg"));
    assertFalse(regexExcImp.matchJpeg("filename.png"));
    assertFalse(regexExcImp.matchJpeg("filename.pdf"));
  }

  /**
   * Test for matchIp method
   */
  @Test
  void matchIpTest() {
    assertTrue(regexExcImp.matchIp("168.192.0.0"));
    assertTrue(regexExcImp.matchIp("0.0.0.0"));
    assertFalse(regexExcImp.matchIp("0."));
    assertFalse(regexExcImp.matchIp("0.9..."));
  }

  /**
   * Test for isEmptyLine method
   */

  @Test
  void isEmptyLineTest() {
    assertTrue(regexExcImp.isEmptyLine("   \t   \n   "));
    assertFalse(regexExcImp.isEmptyLine("Hi"));
  }
}