package site._60jong.jdbc.lecture.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import site._60jong.jdbc.lecture.domain.member.Member;
import site._60jong.jdbc.lecture.respository.MemberRepositoryV3;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static site._60jong.jdbc.lecture.connection.ConnectionConst.*;

class MemberServiceV3_2Test {

    private static final String MEMBER_A_NAME = "memberA";
    private static final String MEMBER_B_NAME = "memberB";
    private static final String MEMBER_EX_NAME = "ex";

    private MemberServiceV3_2 memberService;
    private MemberRepositoryV3 memberRepository;

    @BeforeEach
    void beforeEach() {
        DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);

        memberRepository = new MemberRepositoryV3(dataSource);
        memberService = new MemberServiceV3_2(transactionManager, memberRepository);
    }

    @AfterEach
    void afterEach() throws SQLException {
        memberRepository.deleteAll();
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