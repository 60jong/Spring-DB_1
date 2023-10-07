package site._60jong.jdbc.querydsl.domain.hide;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor @AllArgsConstructor
@Getter
@Embeddable
public class HiddenTopicId implements Serializable {

    @Column(name = "users_id")
    private Long userId;
    private Long topicId;
}
