package site._60jong.jdbc.datasource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import site._60jong.jdbc.connection.ConnectionConst;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static site._60jong.jdbc.connection.ConnectionConst.*;

@Slf4j
public class DriverManagerDataSourceTest {

    @Test
    void getConnectionByDriverManager() throws SQLException {
        Connection conn1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Connection conn2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        log.info("conn = {}, conn class = {}", conn1, conn1.getClass());
        log.info("conn = {}, conn class = {}", conn2, conn2.getClass());
    }

    @Test
    void getConnectionByDriverManagerDataSource() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        userDataSource(dataSource);
    }

    @Test
    void getConnectionInConnectionPool() throws SQLException, InterruptedException {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");

        userDataSource(dataSource);
        Thread.sleep(1000);
    }

    void userDataSource(DataSource dataSource) throws SQLException {
        Connection conn1 = dataSource.getConnection();
        Connection conn2 = dataSource.getConnection();
        Connection conn3 = dataSource.getConnection();
        Connection conn4 = dataSource.getConnection();
        Connection conn5 = dataSource.getConnection();
        Connection conn6 = dataSource.getConnection();
        Connection conn7 = dataSource.getConnection();
        Connection conn8 = dataSource.getConnection();
        Connection conn9 = dataSource.getConnection();
        Connection conn10 = dataSource.getConnection();
        Connection conn11 = dataSource.getConnection();



        log.info("conn = {}, conn class = {}", conn1, conn1.getClass());
        log.info("conn = {}, conn class = {}", conn2, conn2.getClass());
    }
}
