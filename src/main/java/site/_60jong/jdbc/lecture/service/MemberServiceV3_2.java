package site._60jong.jdbc.lecture.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import site._60jong.jdbc.lecture.domain.member.Member;
import site._60jong.jdbc.lecture.respository.MemberRepositoryV3;

import java.sql.SQLException;

/**
 * 트랜잭션 템플릿 사용
 */
public class MemberServiceV3_2 {

    private final TransactionTemplate txTemplate;
    private final MemberRepositoryV3 memberRepository;

    public MemberServiceV3_2(PlatformTransactionManager transactionManager, MemberRepositoryV3 memberRepository) {
        this.txTemplate = new TransactionTemplate(transactionManager);
        this.memberRepository = memberRepository;
    }

    public void accountTransfer(String fromId, String toId, int money) {
        // 트랜잭션 시작
        txTemplate.executeWithoutResult((status) -> {
            try {
                logic(fromId, toId, money);
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        });



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
