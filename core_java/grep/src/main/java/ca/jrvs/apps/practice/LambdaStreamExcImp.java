package ca.jrvs.apps.practice;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LambdaStreamExcImp implements LambdaStreamExc {

  private static final Logger logger = LoggerFactory.getLogger(LambdaStreamExcImp.class);

  /**
   * Create a String stream from array note: arbitrary number of value will be stored in an array
   *
   * @param strings an array of strings
   * @return a Stream of Strings
   */
  @Override
  public Stream<String> createStrStream(String... strings) {
    return Stream.of(strings);
  }

  /**
   * Convert all strings to uppercase please use createStrStream
   *
   * @param strings an array of Strings
   * @return a stream of strings in uppercase
   */
  @Override
  public Stream<String> toUpperCase(String... strings) {
    return createStrStream(strings).map(String::toUpperCase);
  }

  /**
   * filter strings that contains the pattern e.g. filter(stringStream, "a") will return another
   * stream which no element contains a
   *
   * @param stringStream a stream of strings
   * @param pattern      a specified string pattern
   * @return a Stream of strings which does not contain the pattern
   */
  @Override
  public Stream<String> filter(Stream<String> stringStream, String pattern) {
    return stringStream.filter(string -> !string.contains(pattern));
  }

  /**
   * Create a intStream from an arr[]
   *
   * @param arr an array of integers
   * @return an IntStream from the array
   */
  @Override
  public IntStream createIntStream(int[] arr) {
    return IntStream.of(arr);
  }

  /**
   * Convert a stream to list
   *
   * @param stream of generics
   * @param <E>    a generic type
   * @return a list of objects
   */
  @Override
  public <E> List<E> toList(Stream<E> stream) {
    return stream.collect(Collectors.toList());
  }

  /**
   * Convert a intStream to list
   *
   * @param intStream a stream of int
   * @return a list from intStream
   */
  @Override
  public List<Integer> toList(IntStream intStream) {
    return intStream.boxed().collect(Collectors.toList());
  }

  /**
   * Create a IntStream range from start to end inclusive
   *
   * @param start stream range start point inclusive
   * @param end   stream range end point inclusive
   * @return An IntStream from start to end
   */
  @Override
  public IntStream createStream(int start, int end) {
    return IntStream.rangeClosed(start, end);
  }

  /**
   * Convert a intStream to a doubleStream and compute square root of each element
   *
   * @param intStream stream of integers
   * @return a DoubleStream of the input
   */
  @Override
  public DoubleStream squareRootIntStream(IntStream intStream) {
    return intStream.mapToDouble(value -> Math.pow(value, 0.5));
  }

  /**
   * filter all even number and return odd numbers from a intStream
   *
   * @param intStream stream of ints
   * @return stream with only odd numbers
   */
  @Override
  public IntStream getOdd(IntStream intStream) {
    return intStream.filter(integer -> integer % 2 != 0);
  }

  /**
   * Return a lambda function that print a message with a prefix and suffix This lambda can be
   * useful to format logs You will learn: - functional interface <a
   * href="http://bit.ly/2pTXRwM">...</a> & <a href="http://bit.ly/33onFig">...</a> - lambda syntax
   * e.g. LambdaStreamExc lse = new LambdaStreamImp(); Consumer<String> printer =
   * lse.getLambdaPrinter("start>", "<end"); printer.accept("Message body"); output: start>Message
   * body<end
   *
   * @param prefix prefix str
   * @param suffix suffix str
   * @return a lambda function
   */
  @Override
  public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
    return (messageBody) -> logger.info(prefix + messageBody + suffix);
  }

  /**
   * Print each message with a given printer Please use `getLambdaPrinter` method e.g. String[]
   * messages = {"a","b", "c"}; lse.printMessages(messages, lse.getLambdaPrinter("msg:", "!") );
   * output: msg:a! msg:b! msg:c!
   *
   * @param messages an array of message body
   * @param printer  get using the getLambdaPrinter
   */

  @Override
  public void printMessages(String[] messages, Consumer<String> printer) {
    Stream.of(messages).forEach(printer);
  }

  /**
   * Print all odd number from a intStream. Please use `createIntStream` and `getLambdaPrinter`
   * methods e.g. lse.printOdd(lse.createIntStream(0, 5), lse.getLambdaPrinter("odd number:", "!"));
   * output: odd number:1! odd number:3! odd number:5!
   *
   * @param intStream a stream of ints
   * @param printer   a Consumer
   */
  @Override
  public void printOdd(IntStream intStream, Consumer<String> printer) {
    getOdd(intStream).forEach(integer -> printer.accept(String.valueOf(integer)));
  }

  /**
   * Square each number from the input. Please write two solutions and compare difference - using
   * flatMap
   *
   * @param ints Nested list of ints
   * @return the square root of each integer in the lists
   */
  @Override
  public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
    return ints.flatMap(List::stream).map(integer -> integer * integer);
  }
}
