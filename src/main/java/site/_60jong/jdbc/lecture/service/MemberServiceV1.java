package site._60jong.jdbc.lecture.service;

import lombok.RequiredArgsConstructor;
import site._60jong.jdbc.lecture.domain.member.Member;
import site._60jong.jdbc.lecture.respository.MemberRepositoryV1;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV1 {

    private final MemberRepositoryV1 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
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
