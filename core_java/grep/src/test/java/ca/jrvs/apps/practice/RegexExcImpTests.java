package ca.jrvs.apps.practice;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

  public static class LambdaStreamExcImpTests {

    private static final Logger logger = LoggerFactory.getLogger(LambdaStreamExcImp.class);
    private final LambdaStreamExcImp lambdaStreamExcImp;
    private final String[] STRING_ARRAY = {"hello"};


    public LambdaStreamExcImpTests() {
      this.lambdaStreamExcImp = new LambdaStreamExcImp();
    }

    @Test
    public void createStrStreamTest() {
      Stream<String> actual = lambdaStreamExcImp.createStrStream(STRING_ARRAY);
      assertTrue(actual instanceof Stream);

    }

    @Test
    public void toUpperCaseTest() {
      Stream<String> actual = lambdaStreamExcImp.toUpperCase(STRING_ARRAY);
      assertTrue(actual.allMatch(str -> str.equals(str.toUpperCase())));
    }

    @Test
    public void filterTest() {
      Stream<String> actual = lambdaStreamExcImp.createStrStream(STRING_ARRAY);
      String pattern = "p";
      assertTrue(actual.noneMatch(str -> str.contains(pattern)));
    }

    @Test
    public void createIntStreamTest() {
      int[] arr = {1, 3, 4, 5, 2};
      Object actualObject = lambdaStreamExcImp.createIntStream(arr);
      assertTrue(actualObject instanceof IntStream);
    }

    @Test
    public void createStreamTest() {
      int start = 2;
      int end = 8;
      IntStream actual = lambdaStreamExcImp.createStream(start, end);
      assertTrue(actual.allMatch(value -> value >= start && value <= end));
    }

    @Test
    public void squareRootIntStreamTest() {
      IntStream intStream = IntStream.of(1, 4, 9, 16);
      DoubleStream actual = lambdaStreamExcImp.squareRootIntStream(intStream);
      double[] expected = {1.0, 2.0, 3.0, 4.0};
      assertArrayEquals(expected, actual.toArray(), 0.0);
    }

    @Test
    public void getOddTests() {
      IntStream intStream = IntStream.of(1, 3, 5, 7);
      IntStream actual = lambdaStreamExcImp.getOdd(intStream);
      int[] expected = {1, 3, 5, 7};
      assertArrayEquals(expected, actual.toArray());
    }

  }
}
