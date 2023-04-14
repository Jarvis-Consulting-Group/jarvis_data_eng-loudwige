package ca.jrvs.apps.grep;

import ca.jrvs.apps.grep.exceptions.FileReadException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepLambdaImp implements JavaGrepLambda {

  private static final Logger logger = LoggerFactory.getLogger(JavaGrep.class);
  private String regex;
  private String rootPath;
  private String outFile;

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("Usage: JavaGrep regex rootPath outfile");
    }
    JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
    javaGrepLambdaImp.setRegex(args[0]);
    javaGrepLambdaImp.setRootPath(args[1]);
    javaGrepLambdaImp.setOutFile(args[2]);

    try {
      javaGrepLambdaImp.process();
    } catch (IOException e) {
      logger.info(e.getMessage());
    }
  }

  /**
   * Top level Search workflow
   *
   * @throws IOException if the rootPath is not found
   */
  @Override
  public void process() throws IOException {
    List<String> matchedLines = listFiles(rootPath)
        .flatMap(file -> {
          try {
            return readLines(file)
                .filter(this::containsPattern)
                .map(line -> line + System.lineSeparator());
          } catch (FileReadException e) {
            throw new RuntimeException(e);
          }
        })
        .collect(Collectors.toList());
    writeToFile(matchedLines);
  }

  /**
   * Takes a root directory and recursively return the files
   *
   * @param rootDir input directory
   * @return a Stream of files
   * @throws FileNotFoundException if the rootDir does not exist
   */
  @Override
  public Stream<File> listFiles(String rootDir) throws FileNotFoundException {
    File files = new File(rootDir);
    try (Stream<File> fileStream = Arrays.stream(Objects.requireNonNull(files.listFiles()))
        .flatMap(file -> {
          try {
            return file.isDirectory() ? listFiles(file.getAbsolutePath()) : Stream.of(file);
          } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
          }
        })
    ) {
      return fileStream.collect(Collectors.toList()).stream();
    }
  }

  /**
   * Read a file line by line
   *
   * @param inputFile file to be read
   * @return a stream of string lines
   * @throws IllegalArgumentException if file does not exist
   * @throws FileReadException        if the file cannot be read
   */
  @Override
  public Stream<String> readLines(File inputFile)
      throws IllegalArgumentException, FileReadException {
    Objects.requireNonNull(inputFile, "File parameter cannot be null");
    String absolutePath = inputFile.getAbsolutePath();
    if (!inputFile.exists()) {
      throw new IllegalArgumentException("File does not exist: " + absolutePath);
    }
    try {
      //No need for try-with-resources, the BufferedReader is already closed in Files.lines method
      return Files.lines(inputFile.toPath());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Search for a pattern in a giving line
   *
   * @param line input string
   * @return true if the line contains the pattern and false if not
   */
  @Override
  public boolean containsPattern(String line) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(line);
    return matcher.matches();
  }

  /**
   * Open a file and write the matched lines
   *
   * @param lines matched lines
   * @throws IOException if the writing failed
   */
  @Override
  public void writeToFile(List<String> lines) throws IOException {
    try (
        BufferedWriter outFileWriter = new BufferedWriter(new OutputStreamWriter(
            Files.newOutputStream(Paths.get(outFile))))) {
      lines.forEach(line -> {
        try {
          outFileWriter.write(line);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        try {
          outFileWriter.newLine();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      });
    } catch (IOException ex) {
      throw new IOException("Error writing to file: " + outFile);
    }
  }

  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getRootPath() {
    return rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getOutFile() {
    return outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }

}
