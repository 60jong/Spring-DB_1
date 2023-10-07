package site._60jong.jdbc.querydsl.domain.vote;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor @AllArgsConstructor
@Embeddable
public class VoteId implements Serializable {

    @Column(name = "users_id")
    private Long userId;
    private Long topicId;
}
