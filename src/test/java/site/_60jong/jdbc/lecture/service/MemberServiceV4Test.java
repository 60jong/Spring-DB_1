package site._60jong.jdbc.lecture.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import site._60jong.jdbc.lecture.domain.member.Member;
import site._60jong.jdbc.lecture.respository.MemberRepository;
import site._60jong.jdbc.lecture.respository.MemberRepositoryV4_1;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
@SpringBootTest
class MemberServiceV4Test {

    private static final String MEMBER_A_NAME = "memberA";
    private static final String MEMBER_B_NAME = "memberB";
    private static final String MEMBER_EX_NAME = "ex";

    @Autowired
    private MemberServiceV4 memberService;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void afterEach() {
        memberRepository.delete(MEMBER_A_NAME);
        memberRepository.delete(MEMBER_B_NAME);
        memberRepository.delete(MEMBER_EX_NAME);
    }

    @Test
    void AopCheck() {
        log.info("memberService is Proxy : {}", AopUtils.isAopProxy(memberService));
    }

    @Test
    @DisplayName("정상 계좌이체")
    void accountTransferTest() {
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
    void accountTransfer_exception_Test() {
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


    @TestConfiguration
    static class TestConfig {
        @Bean
        public MemberRepository memberRepository(DataSource dataSource) {
            return new MemberRepositoryV4_1(dataSource);
        }

        @Bean
        public MemberServiceV4 memberService(MemberRepository memberRepository) {
            return new MemberServiceV4(memberRepository);
        }
    }
}



