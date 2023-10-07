package site._60jong.jdbc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import site._60jong.jdbc.domain.member.Member;
import site._60jong.jdbc.respository.MemberRepositoryV3;

import java.sql.SQLException;

/**
 * TransactionTemplate
 */
public class MemberServiceV3_2 {

    private final TransactionTemplate template;
    private final MemberRepositoryV3 memberRepository;

    public MemberServiceV3_2(PlatformTransactionManager transactionManager, MemberRepositoryV3 memberRepository) {
        this.template = new TransactionTemplate(transactionManager);
        this.memberRepository = memberRepository;
    }

    public Member retrieveById(String memberId) throws SQLException {
        template.execute(status -> {
                try {
                    return memberRepository.findById(memberId);
                } catch (Exception e) {
                    throw new IllegalStateException();
                }
            }
        );
        return null;
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
        template.executeWithoutResult(status -> {
            try {
                // logic
                accountTransferLogic(fromId, toId, money);
            } catch (Exception e) {
                throw new IllegalStateException();
            }
        });
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
