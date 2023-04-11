package ca.jrvs.practice.dataStructure;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Main {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);
  public static void main(String[] args) {
    BasicConfigurator.configure();
    double value = 3 * Math.log(16) / Math.log(2);
    logger.info("The value is " + value);
  }

}
