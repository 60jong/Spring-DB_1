package site._60jong.jdbc.querydsl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site._60jong.jdbc.querydsl.domain.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
