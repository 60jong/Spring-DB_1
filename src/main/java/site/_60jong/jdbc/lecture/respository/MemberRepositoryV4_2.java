package site._60jong.jdbc.lecture.respository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import site._60jong.jdbc.lecture.domain.member.Member;
import site._60jong.jdbc.lecture.respository.exception.MyDbException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SqlExceptionTranslator 사용
 */
@Slf4j
public class MemberRepositoryV4_2 implements MemberRepository {

    private final DataSource dataSource;
    private final SQLExceptionTranslator exTranslator;

    public MemberRepositoryV4_2(DataSource dataSource) {
        this.dataSource = dataSource;
        this.exTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
    }

    @Override
    public Member save(Member member) {

        String query = "insert into member(member_id,money) values(?,?);";

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            throw exTranslator.translate("save", query, e);
        } finally {
            close(con, pstmt, null);
        }
    }

    @Override
    public Member findById(String id) {

        String query = "select * from member where member_id = ?;";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Member(
                        rs.getString("member_id"),
                        rs.getInt("money")
                );
            }

            throw new IllegalArgumentException("wrong member-id");
        } catch (SQLException e) {
            throw exTranslator.translate("select", query, e);
        } finally {
            close(con, pstmt, rs);
        }
    }

    @Override
    public void updateMoney(String memberId, int money) {

        String query = "update member set money = ? where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw exTranslator.translate("update", query, e);
        } finally {
            close(con, pstmt, null);
        }
    }

    @Override
    public void delete(String memberId) {

        String query = "delete from member where member_id = ?;";

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, memberId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw exTranslator.translate("delete", query, e);
        } finally {
            close(con, pstmt, null);
        }
    }

    private Connection getConnection() {
        Connection con = DataSourceUtils.getConnection(dataSource);
        log.info("connection : {}", con);
        return con;
    }

    private void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(pstmt);
        DataSourceUtils.releaseConnection(con, dataSource);
    }

}
