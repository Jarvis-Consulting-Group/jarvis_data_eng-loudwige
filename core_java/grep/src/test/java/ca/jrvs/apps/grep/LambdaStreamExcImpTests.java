package ca.jrvs.apps.grep;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.jrvs.apps.practice.LambdaStreamExcImp;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LambdaStreamExcImpTests {

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
