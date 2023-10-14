package site._60jong.jdbc.lecture.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;
import site._60jong.jdbc.lecture.domain.member.Member;
import site._60jong.jdbc.lecture.respository.MemberRepositoryV1;
import site._60jong.jdbc.lecture.respository.MemberRepositoryV2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Transaction 사용
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection con = null;

        try {
            con = dataSource.getConnection();
            con.setAutoCommit(false);

            logic(fromId, toId, money, con);

            con.commit();
        } catch (Exception e) {
            log.info("db error", e);
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(true);
            closeConnection(con);
        }

    }

    private void logic(String fromId, String toId, int money, Connection con) throws SQLException {
        Member fromMember = memberRepository.findById(fromId, con);
        Member toMember = memberRepository.findById(toId, con);

        memberRepository.updateMoney(fromId, fromMember.getMoney() - money, con);
        validate(toId);
        memberRepository.updateMoney(toId, toMember.getMoney() + money, con);
    }

    public void deleteAll() throws SQLException {
        memberRepository.deleteAll(dataSource.getConnection());
    }

    private void validate(String id) {
        if (id.equals("ex")) {
            throw new IllegalArgumentException("이체 중 예외 발생!");
        }
    }

    private void closeConnection(Connection con) {
        JdbcUtils.closeConnection(con);
    }
}
