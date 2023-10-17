package site._60jong.jdbc.lecture.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import site._60jong.jdbc.lecture.domain.member.Member;
import site._60jong.jdbc.lecture.respository.MemberRepository;
import site._60jong.jdbc.lecture.respository.MemberRepositoryV3;

import java.sql.SQLException;

/**
 * 예외 누수 해결 (언체크 예외에 의존)
 */

@RequiredArgsConstructor
public class MemberServiceV4 {

    private final MemberRepository memberRepository;

    @Transactional
    public void accountTransfer(String fromId, String toId, int money) {
        logic(fromId, toId, money);
    }

    private void logic(String fromId, String toId, int money) {
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
