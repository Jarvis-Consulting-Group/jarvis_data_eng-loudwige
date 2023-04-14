package ca.jrvs.apps.grep;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.jrvs.apps.grep.exceptions.FileReadException;
import ca.jrvs.apps.grep.exceptions.ListFilesException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class JavaGrepLambdaTests {

  private final JavaGrepLambdaImp javaGrepLambdaImp;

  public JavaGrepLambdaTests() {
    this.javaGrepLambdaImp = new JavaGrepLambdaImp();
  }

  @Test
  public void readLinesTest() throws FileReadException {
    File file = new File(
        "/home/centos/dev/jarvis_data_eng_Loudwige/core_java/grep/src/main/java/ca/jrvs/apps/grep/words.txt");
    List<String> expectedLines = Arrays.asList("Hello Everyone!", "How are you doing?", "Good?");
//
    Stream<String> linesStream =  javaGrepLambdaImp.readLines(file);
      List<String> actualLines = linesStream.collect(Collectors.toList());
      assertEquals(expectedLines, actualLines);
    }


  @Test
  public void listFilesRecursively() {
    String rootDir = "/home/centos/dev/jarvis_data_eng_Loudwige/core_java/grep/src/test/java";
    List<String> expectedFileNames = Stream.of("JavaGrepImpTests.java", "RegexExcImpTests.java")
        .collect(Collectors.toList());

    List<String> actualFileNames = null;
    try {
      actualFileNames = javaGrepLambdaImp.listFiles(rootDir)
          .map(File::getName)
          .collect(Collectors.toList());
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    assertEquals(expectedFileNames, actualFileNames);

  }

}
