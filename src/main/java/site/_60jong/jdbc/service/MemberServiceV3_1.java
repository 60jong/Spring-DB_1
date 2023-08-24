package site._60jong.jdbc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import site._60jong.jdbc.domain.member.Member;
import site._60jong.jdbc.respository.MemberRepositoryV2;
import site._60jong.jdbc.respository.MemberRepositoryV3;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * TransactionManager
 */
@RequiredArgsConstructor
public class MemberServiceV3_1 {

    private final PlatformTransactionManager transactionManager;
    private final MemberRepositoryV3 memberRepository;

    public Member retrieveById(String memberId) throws SQLException {
        try {
            return memberRepository.findById(memberId);
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }
    public void join(Member member) throws SQLException {
        try {
            joinLogic(member);
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

    private void joinLogic(Member member) throws SQLException {
        memberRepository.save(member);
    }

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        // 트랜잭션 시작
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            // logic
            accountTransferLogic(fromId, toId, money);
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw new IllegalStateException();
        }
    }

    private void accountTransferLogic(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update( fromId, fromMember.getMoney() - money);
        validate(toId);
        memberRepository.update( toId, toMember.getMoney() + money);
    }

    private void validate(String id) {
        if (id.equals("ex")) {
            throw new IllegalStateException("이체 중 예외 발생!");
        }
    }
}
