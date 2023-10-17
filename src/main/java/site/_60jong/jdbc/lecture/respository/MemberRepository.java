package site._60jong.jdbc.lecture.respository;

import site._60jong.jdbc.lecture.domain.member.Member;

public interface MemberRepository {

    Member save(Member member);

    Member findById(String memberId);

    void updateMoney(String memberId, int money);

    void delete(String memberId);
}
