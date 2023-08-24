package site._60jong.jdbc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import site._60jong.jdbc.domain.member.Member;
import site._60jong.jdbc.respository.MemberRepositoryV2;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;
import static site._60jong.jdbc.connection.ConnectionConst.*;

class MemberServiceV2Test {

    private static final String MEMBER_A = "memberA";
    private static final String MEMBER_B = "memberB";
    private static final String MEMBER_EX = "ex";

    private DataSource dataSource;
    private MemberRepositoryV2 memberRepository;
    private MemberServiceV2 memberService;

    @BeforeEach
    void beforeEach() {
        dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepository = new MemberRepositoryV2(dataSource);
        memberService = new MemberServiceV2(dataSource, memberRepository);
    }

    @Test
    @DisplayName("정상 이체")
    void accountTransfer() throws SQLException {
        // given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_B, 10000);
        memberService.join(memberA);
        memberService.join(memberB);

        // when
        memberService.accountTransfer(MEMBER_A, MEMBER_B, 1000);

        // then
        Member findMemberA = memberService.retrieveById(MEMBER_A);
        Member findMemberB = memberService.retrieveById(MEMBER_B);

        assertThat(findMemberA.getMoney()).isEqualTo(9000);
        assertThat(findMemberB.getMoney()).isEqualTo(11000);
    }

    @Test
    @DisplayName("이체중 예외 발생")
    void accountTransfer_exception() throws SQLException {
        // given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberEx = new Member(MEMBER_EX, 10000);
        memberService.join(memberA);
        memberService.join(memberEx);

        // when
        assertThatThrownBy(() -> memberService.accountTransfer(MEMBER_A, MEMBER_EX, 1000))
                .isInstanceOf(IllegalStateException.class);

        // then
        Member findMemberA = memberService.retrieveById(MEMBER_A);
        Member findMemberEx = memberService.retrieveById(MEMBER_EX);

        assertThat(findMemberA.getMoney()).isEqualTo(10000);
        assertThat(findMemberEx.getMoney()).isEqualTo(10000);
    }

}