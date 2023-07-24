package site._60jong.jdbc.compare;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class CompareTest {

    @Test
    void equals_test() {
        Member memberA = new Member(1L, 25, "ykj");
        Member memberB = new Member(1L, 24, "jej");

        log.info("memberA equals memberB : {}", memberA.equals(memberB));
        log.info("memberA == memberB : {}", memberA == memberB);
    }

    @ToString
    @Getter
    static class Member {

        private Long id;
        private int age;
        private String name;

        public Member(Long id, int age, String name) {
            this.id = id;
            this.age = age;
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            Member anotherMember = (Member) obj;
            return this.id == anotherMember.id;
        }
    }
}
