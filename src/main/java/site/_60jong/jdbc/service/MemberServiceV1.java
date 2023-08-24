package site._60jong.jdbc.service;

import lombok.RequiredArgsConstructor;
import site._60jong.jdbc.domain.member.Member;
import site._60jong.jdbc.respository.MemberRepositoryV1;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV1 {

    private final MemberRepositoryV1 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        validate(toId);
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private void validate(String id) {
        if (id.equals("ex")) {
            throw new IllegalStateException("이체 중 예외 발생!");
        }
    }
}
