package ca.jrvs.apps.grep;

import ca.jrvs.apps.grep.exceptions.ListFilesException;
import ca.jrvs.apps.grep.exceptions.FileReadException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImp implements JavaGrep {

  private static final Logger logger = LoggerFactory.getLogger(JavaGrep.class);
  private String regex;
  private String rootPath;
  private String outFile;

  /**
   * Top level search workflow
   *
   * @throws IOException If the rootPath is not found
   */

  @Override
  public void process() throws IOException {
    List<String> matchedLines = new ArrayList<>();
    List<File> files = listFiles(rootPath);
    for (File file : files) {
      List<String> lines = readLines(file);
      for (String line : lines) {
        if (containsPattern(line)) {
          matchedLines.add(line);
        }
      }
    }
    writeToFile(matchedLines);
  }

  /**
   * Traverse a given directory and return all files
   *
   * @param rootDir input directory
   * @return files under the root directory
   */
  @Override
  public List<File> listFiles(String rootDir) throws ListFilesException {
    List<File> fileslist = new ArrayList<>();
    File file = new File(rootDir);
    File[] files = file.listFiles();
    if ((files != null ? files.length : 0) == 0) {
      throw new ListFilesException("The directory is empty: " + file.getName());
    }
    for (File filename : files) {
      if (filename.isDirectory()) {
        fileslist.addAll(listFiles(filename.getAbsolutePath()));
      } else {
        fileslist.add(filename);
      }
    }

    return fileslist;
  }

  /**
   * Read a file and return all the lines
   *
   * @param inputFile file to be read
   * @return lines
   * @throws IllegalArgumentException if a given inputFile is not a file
   */
  @Override
  public List<String> readLines(File inputFile) throws IllegalArgumentException, FileReadException {
    Objects.requireNonNull(inputFile, "File parameter cannot be null");
    String absolutePath = inputFile.getAbsolutePath();
    if (!inputFile.exists()) {
      throw new IllegalArgumentException("File does not exist: " + absolutePath);
    }
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
      List<String> lines = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
      return lines;
    } catch (IOException e) {
      throw new FileReadException("Error reading file: " + absolutePath, e);
    }
  }

  /**
   * Check if a line contains the regex pattern (passed by user)
   *
   * @param line input string
   * @return true if there is a match
   */
  @Override
  public boolean containsPattern(String line) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(line);
    return matcher.matches();
  }

  /**
   * Write lines to file
   *
   * @param lines matched lines
   * @throws IOException if write failed
   */
  @Override
  public void writeToFile(List<String> lines) throws IOException {
    try (BufferedWriter outFileWriter = new BufferedWriter(new OutputStreamWriter(
        Files.newOutputStream(Paths.get(outFile))))) {
      for (String line : lines) {
        outFileWriter.write(line);
        outFileWriter.newLine();
      }
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

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("Usage: JavaGrep regex rootPath outfile");
    }
    BasicConfigurator.configure();

    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
      javaGrepImp.process();
    } catch (Exception ex) {
      logger.error("Error: Unable to process", ex);
    }
  }
}
