package site._60jong.jdbc.respository;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import site._60jong.jdbc.connection.ConnectionConst;
import site._60jong.jdbc.domain.member.Member;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static site._60jong.jdbc.connection.ConnectionConst.*;

@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach() {
        /**
         * DriverManagerDataSource 사용
         * DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
         * repository = new MemberRepositoryV1(dataSource);
         */

        /**
         * HikariDataSource 사용
         */
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");

        repository = new MemberRepositoryV1(dataSource);
    }

    @Test
    void member_save_test() throws SQLException {
        // given
        Member member = new Member("YKJ0", 10000);

        // when
        Member save = repository.save(member);

        // then
        assertThat(save.getMemberId()).isEqualTo(member.getMemberId());
    }

    @Test
    void member_save_and_find_test() throws SQLException {
        // given
        String memberId = "yjk8";
        int money = 10000;

        Member member = new Member(memberId, money);
        repository.save(member);

        // when
        Member findMember = repository.findById(memberId);

        // then
        assertThat(findMember.getMoney()).isEqualTo(money);
    }

    @Test
    void member_save_and_money_update() throws SQLException {
        // given
        String memberId = "yjk5";
        int money = 10000;

        Member member = new Member(memberId, money);
        repository.save(member);

        // when
        repository.update(memberId, 20000);

        // then
        assertThat(repository.findById(memberId).getMoney()).isEqualTo(20000);
    }

    @Test
    void member_save_and_delete() throws SQLException {
        // given
        String memberId = "yjkj";
        int money = 10000;

        Member member = new Member(memberId, money);
        repository.save(member);

        // when
        repository.delete(memberId);

        // then
        assertThatThrownBy(() -> repository.findById(memberId).getMoney()).isInstanceOf(NoSuchElementException.class);

    }
}