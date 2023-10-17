package site._60jong.jdbc.lecture.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import site._60jong.jdbc.practice.connection.ConnectionConst;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;
import static site._60jong.jdbc.practice.connection.ConnectionConst.*;

@Slf4j
public class SqlExceptionTest {

    DataSource dataSource;

    @BeforeEach
    void before() {
        dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
    }

    @Test
    void bad_grammer_translate() {
        // given
        String query = "select bad grammer";

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.executeQuery();
        } catch (SQLException e) {
            SQLErrorCodeSQLExceptionTranslator exTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
            DataAccessException ex = exTranslator.translate("select", query, e);
            assertThat(ex).isInstanceOf(BadSqlGrammarException.class);
        }
    }
}
