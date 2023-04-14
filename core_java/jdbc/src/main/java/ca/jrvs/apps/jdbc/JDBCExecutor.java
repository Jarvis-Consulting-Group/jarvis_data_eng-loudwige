package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCExecutor {

  private static final Logger logger = LoggerFactory.getLogger(JDBCExecutor.class);

  public static void main(String[] args) {
    BasicConfigurator.configure();
    logger.info("Hello Learning JDBC");
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "5432",
        "hplussport", "postgres", "password");
    try {
      Connection connection = dcm.getConnection();
      OrderDAO orderDAO = new OrderDAO(connection);
      Order order = orderDAO.findById(1000);
      logger.info(order.toString());
    } catch (SQLException e) {
      logger.debug(e.getMessage());
    }
  }
}
