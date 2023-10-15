package site._60jong.jdbc.lecture.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import site._60jong.jdbc.lecture.domain.member.Member;
import site._60jong.jdbc.lecture.respository.MemberRepositoryV3;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV3_1 {

    private final PlatformTransactionManager transactionManager;
    private final MemberRepositoryV3 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        // 트랜잭션 시작
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            // 비즈니스 로직
            logic(fromId, toId, money);

            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    private void logic(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.updateMoney(fromId, fromMember.getMoney() - money);
        validate(toId);
        memberRepository.updateMoney(toId, toMember.getMoney() + money);
    }

    private void validate(String id) {
        if (id.equals("ex")) {
            throw new IllegalArgumentException("이체 중 예외 발생!");
        }
    }
}
