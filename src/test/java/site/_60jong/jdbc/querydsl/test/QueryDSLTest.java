package site._60jong.jdbc.querydsl.test;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.support.FetchableSubQueryBase;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import site._60jong.jdbc.querydsl.config.QueryDSLConfig;
import site._60jong.jdbc.querydsl.domain.QTopic;
import site._60jong.jdbc.querydsl.domain.QUser;
import site._60jong.jdbc.querydsl.domain.Topic;
import site._60jong.jdbc.querydsl.domain.User;
import site._60jong.jdbc.querydsl.domain.hide.QHiddenTopic;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QueryDSLConfig.class)
public class QueryDSLTest {

    @Autowired
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    void findUser_By_name() {
        // given
        String username = "usernameA";
        String email = "emailA";

        User newUser = new User(username, email);

        em.persist(newUser);

        // when
        QUser user = QUser.user;
        User findUser = queryFactory
                .selectFrom(user)
                .where(user.name.eq(username))
                .fetchOne();

        // then
        assertThat(findUser).isEqualTo(newUser);
    }

    @Test
    void find_topic_not_hidden() {
        // given
        Long userId = 4L;
        Long categoryId = 1L;
        // when
        QTopic topic = QTopic.topic;
        QHiddenTopic ht = QHiddenTopic.hiddenTopic;
        List<Topic> findTopics = queryFactory
                .selectFrom(topic)
                .join(topic.publishUser).fetchJoin()
                    .where(eqCategory(categoryId), notHiddenBy(userId))
                .fetch();

        // then
        System.out.println(findTopics);
    }

    private BooleanExpression notHiddenBy(Long userId) {
        QTopic t = QTopic.topic;
        QHiddenTopic ht = QHiddenTopic.hiddenTopic;
        return JPAExpressions
                .selectFrom(ht)
                .where(ht.id.topicId.eq(t.id), ht.id.userId.eq(userId))
                .notExists();
    }

    private BooleanExpression eqCategory(Long categoryId) {
        QTopic t = QTopic.topic;
        return t.category.id.eq(categoryId);
    }
}
