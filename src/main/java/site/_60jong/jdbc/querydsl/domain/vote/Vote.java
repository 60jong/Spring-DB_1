package site._60jong.jdbc.querydsl.domain.vote;

import jakarta.persistence.*;
import lombok.Getter;
import site._60jong.jdbc.querydsl.domain.Topic;
import site._60jong.jdbc.querydsl.domain.User;

@Getter
@Entity
public class Vote {

    @EmbeddedId
    private VoteId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @MapsId("topicId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Enumerated(EnumType.STRING)
    private VoteSide side;


    public Vote(User user, Topic topic, VoteSide side) {
        this.user = user;
        user.addVote(this);

        this.topic = topic;
        topic.addVote(this);

        this.side = side;
    }
}
