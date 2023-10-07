package site._60jong.jdbc.querydsl.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site._60jong.jdbc.querydsl.domain.hide.HiddenTopic;
import site._60jong.jdbc.querydsl.domain.vote.Vote;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "publishUser")
    private List<Topic> publishedTopics = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Vote> votes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<HiddenTopic> hiddenTopics = new ArrayList<>();

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void addPublishedTopic(Topic topic) {
        this.publishedTopics.add(topic);
    }

    public void addVote(Vote vote) {
        this.votes.add(vote);
    }

    public void hideTopic(HiddenTopic hiddenTopic) {
        this.hiddenTopics.add(hiddenTopic);
    }
}
