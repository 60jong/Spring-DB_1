package site._60jong.jdbc.lecture.respository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;
import site._60jong.jdbc.lecture.domain.member.Member;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Connection을 파라미터로 받음
 */
@Slf4j
@RequiredArgsConstructor
public class MemberRepositoryV2 {

    private final DataSource dataSource;

    public Member save(Member member) throws SQLException {

        String query = "insert into member(member_id,money) values(?,?);";

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.info("insert error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }

    public Member findById(String id, Connection con) throws SQLException {

        String query = "select * from member where member_id = ?;";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
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
            log.info("insert error", e);
            throw e;
        } finally {
            close(null, pstmt, rs);
        }
    }

    public Member findById(String id) throws SQLException {
        return findById(id, dataSource.getConnection());
    }

    public void updateMoney(String memberId, int money, Connection con) throws SQLException {

        String query = "update member set money = ? where member_id = ?";

        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.info("insert error", e);
            throw e;
        } finally {
            close(null, pstmt, null);
        }
    }

    public void delete(String memberId, Connection con) throws SQLException {

        String query = "delete from member where member_id = ?;";

        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, memberId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.info("insert error", e);
            throw e;
        } finally {
            close(null, pstmt, null);
        }
    }

    public void deleteAll(Connection con) throws SQLException {

        String query = "delete from member;";

        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.info("insert error", e);
            throw e;
        } finally {
            close(null, pstmt, null);
        }
    }

    private void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(pstmt);
        JdbcUtils.closeConnection(con);
    }

}
