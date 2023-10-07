package site._60jong.jdbc.querydsl.domain.hide;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site._60jong.jdbc.querydsl.domain.Topic;
import site._60jong.jdbc.querydsl.domain.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class HiddenTopic {

    @EmbeddedId
    private HiddenTopicId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @MapsId("topicId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    public HiddenTopic(User user, Topic topic) {
        this.user = user;
        user.hideTopic(this);

        this.topic = topic;
        topic.addHide(this);
    }
}
