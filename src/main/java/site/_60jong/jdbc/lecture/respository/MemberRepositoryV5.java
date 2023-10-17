package site._60jong.jdbc.lecture.respository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import site._60jong.jdbc.lecture.domain.member.Member;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SqlExceptionTranslator 사용
 */
@Slf4j
public class MemberRepositoryV5 implements MemberRepository {

    private final DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public MemberRepositoryV5(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {

        String query = "insert into member(member_id,money) values(?,?);";

        jdbcTemplate.update(query, member.getMemberId(), member.getMoney());
        return member;
    }

    @Override
    public Member findById(String id) {

        String query = "select * from member where member_id = ?;";

        return jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> new Member(
                        rs.getString("member_id"),
                        rs.getInt("money")
                ), id);
    }

    @Override
    public void updateMoney(String memberId, int money) {

        String query = "update member set money = ? where member_id = ?";

        jdbcTemplate.update(query, money, memberId);
    }

    @Override
    public void delete(String memberId) {

        String query = "delete from member where member_id = ?;";

        jdbcTemplate.update(query, memberId);
    }
}
