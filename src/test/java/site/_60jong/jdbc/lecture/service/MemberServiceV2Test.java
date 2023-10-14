package site._60jong.jdbc.lecture.service;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import site._60jong.jdbc.lecture.domain.member.Member;
import site._60jong.jdbc.lecture.respository.MemberRepositoryV1;
import site._60jong.jdbc.lecture.respository.MemberRepositoryV2;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static site._60jong.jdbc.lecture.connection.ConnectionConst.*;

class MemberServiceV2Test {

    private static final String MEMBER_A_NAME = "memberA";
    private static final String MEMBER_B_NAME = "memberB";
    private static final String MEMBER_EX_NAME = "ex";

    MemberRepositoryV2 memberRepository;
    MemberServiceV2 memberService;

    @BeforeEach
    void beforeEach() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setPoolName("MyPool_V1");
        hikariDataSource.setJdbcUrl(URL);
        hikariDataSource.setUsername(USERNAME);
        hikariDataSource.setPassword(PASSWORD);
        hikariDataSource.setMaximumPoolSize(10);

        memberRepository = new MemberRepositoryV2(hikariDataSource);
        memberService = new MemberServiceV2(hikariDataSource, memberRepository);
    }

    @AfterEach
    void afterEach() throws SQLException {
        memberService.deleteAll();
    }

    @Test
    @DisplayName("정상 계좌이체")
    void accountTransferTest() throws SQLException {
        // given
        int initMoney = 10000;
        int transferMoney = 5000;

        Member memberA = new Member(MEMBER_A_NAME, initMoney);
        Member memberB = new Member(MEMBER_B_NAME, initMoney);

        memberRepository.save(memberA);
        memberRepository.save(memberB);

        // when
        memberService.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), transferMoney);

        // then
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberB = memberRepository.findById(memberB.getMemberId());
        assertAll(
                () -> assertThat(findMemberA.getMoney()).isEqualTo(initMoney - transferMoney),
                () -> assertThat(findMemberB.getMoney()).isEqualTo(initMoney + transferMoney)
        );
    }

    @Test
    @DisplayName("계좌이체 중 예외시 롤백 적용 O")
    void accountTransfer_exception_Test() throws SQLException {
        // given
        int initMoney = 10000;
        int transferMoney = 5000;

        Member memberA = new Member(MEMBER_A_NAME, initMoney);
        Member memberEx = new Member(MEMBER_EX_NAME, initMoney);

        memberRepository.save(memberA);
        memberRepository.save(memberEx);

        // then
        assertAll(
                () -> assertThatThrownBy(
                        () -> memberService.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), transferMoney))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThat(memberRepository.findById(memberA.getMemberId()).getMoney()).isEqualTo(initMoney),
                () -> assertThat(memberRepository.findById(memberEx.getMemberId()).getMoney()).isEqualTo(initMoney)
        );
    }

}