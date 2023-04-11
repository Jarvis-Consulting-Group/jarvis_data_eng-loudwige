package ca.jrvs.practice.dataStructure.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Main {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);
  public static void main(String[] args) {
    BasicConfigurator.configure();
    List<Employee> employees = new ArrayList<>();
    employees.add(new Employee(1, "John", 30, 500000));
    employees.add(new Employee(2, "Jane", 26, 300000));
    employees.add(new Employee(3, "Christian", 40, 800000));

    //Sort employees by salary using Comparable
    Collections.sort(employees);

    //Sort employees by age using Comparator
    employees.sort(Employee.AgeComparator);

    logger.info(employees.toString());
  }
}
