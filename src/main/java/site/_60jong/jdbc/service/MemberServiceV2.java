package site._60jong.jdbc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.support.JdbcUtils;
import site._60jong.jdbc.domain.member.Member;
import site._60jong.jdbc.respository.MemberRepositoryV1;
import site._60jong.jdbc.respository.MemberRepositoryV2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Transaction 사용
 */
@RequiredArgsConstructor
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

    public Member retrieveById(String memberId) throws SQLException {
        Connection conn = getConnection();

        try {
            return memberRepository.findById(conn, memberId);
        } catch (Exception e) {
            throw new IllegalStateException();
        } finally {
            JdbcUtils.closeConnection(conn);
        }
    }
    public void join(Member member) throws SQLException {
        Connection conn = getConnection();

        try {
            conn.setAutoCommit(false); // 트랜잭션 시작

            // logic
            joinLogic(conn, member);

            conn.commit(); // 성공시 트랜잭션 종료
        } catch (Exception e) {
            conn.rollback();
            throw new IllegalStateException();
        } finally {
            conn.setAutoCommit(true); // 커넥션 풀 고려 (설정이 유지 되기 때문에)
            JdbcUtils.closeConnection(conn);
        }
    }

    private void joinLogic(Connection conn, Member member) throws SQLException {
        memberRepository.save(conn, member);
    }

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection conn = getConnection();

        try {
            conn.setAutoCommit(false); // 트랜잭션 시작

            // logic
            accountTransferLogic(conn, fromId, toId, money);

            conn.commit(); // 성공시 트랜잭션 종료
        } catch (Exception e) {
            conn.rollback();
            throw new IllegalStateException();
        } finally {
            conn.setAutoCommit(true); // 커넥션 풀 고려 (설정이 유지 되기 때문에)
            JdbcUtils.closeConnection(conn);
        }
    }

    private void accountTransferLogic(Connection conn, String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(conn, fromId);
        Member toMember = memberRepository.findById(conn, toId);

        memberRepository.update(conn, fromId, fromMember.getMoney() - money);
        validate(toId);
        memberRepository.update(conn, toId, toMember.getMoney() + money);
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private void validate(String id) {
        if (id.equals("ex")) {
            throw new IllegalStateException("이체 중 예외 발생!");
        }
    }
}
