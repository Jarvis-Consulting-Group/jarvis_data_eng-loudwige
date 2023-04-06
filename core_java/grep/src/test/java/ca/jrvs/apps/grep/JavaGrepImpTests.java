package ca.jrvs.apps.grep;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.jrvs.apps.grep.exceptions.EmptyFileException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class JavaGrepImpTests {

  private final JavaGrepImp javaGrepImp;

  public JavaGrepImpTests() {
    this.javaGrepImp = new JavaGrepImp();
  }

  @Test
  public void listFilesFromDirectoryOfFiles() throws EmptyFileException {
    String rootDir = "/home/centos/dev/jarvis_data_eng_Loudwige/core_java/grep/src"
        + "/main/java/ca/jrvs/apps/grep";
    List<File> expectedOutputs = Arrays.stream(Objects.requireNonNull(new File(rootDir)
            .listFiles()))
        .filter(File::isFile)
        .collect(Collectors.toList());
    List<File> actualOutputs = javaGrepImp.listFiles(rootDir);
    assertEquals(expectedOutputs, actualOutputs);
  }

  @Test
  public void listFilesRecursively() {
    String rootDir = "/home/centos/dev/jarvis_data_eng_Loudwige/core_java/grep/src/test/java";
    List<String> expectedFileNames = Stream.of("JavaGrepImpTests.java", "RegexExcImpTests.java")
        .collect(Collectors.toList());

    List<String> actualFileNames = null;
    try {
      actualFileNames = javaGrepImp.listFiles(rootDir)
          .stream()
          .map(File::getName)
          .collect(Collectors.toList());
    } catch (EmptyFileException e) {
      throw new RuntimeException(e);
    }
    assertEquals(expectedFileNames, actualFileNames);

  }

  /**
   * Test the readLine method
   *
   * @throws IllegalArgumentException
   * @throws IOException
   */
  @Test
  public void readLinesTest() throws IllegalArgumentException, IOException {
    File file = new File(
        "/home/centos/dev/jarvis_data_eng_Loudwige/core_java/grep/src/test/java/ca/jrvs/"
            + "apps/grep/words.txt");
    List<String> expectedLines = Stream.of("Hello Everyone!", "How are you doing?", "Good?")
        .collect(Collectors.toList());

    List<String> actualLines = new ArrayList<>(javaGrepImp.readLines(file));
    assertEquals(expectedLines, actualLines);
  }
}