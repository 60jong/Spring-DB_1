package site._60jong.jdbc.querydsl.domain;

import jakarta.persistence.*;
import lombok.Getter;
import site._60jong.jdbc.querydsl.domain.hide.HiddenTopic;
import site._60jong.jdbc.querydsl.domain.vote.Vote;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Topic {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    private User publishUser;

    @OneToMany(mappedBy = "topic")
    private List<HiddenTopic> hides = new ArrayList<>();

    @OneToMany(mappedBy = "topic")
    private List<Vote> votes = new ArrayList<>();

    //==연관관계 편의 메서드==//
    public void setPublishUser(User user) {
        this.publishUser = user;
        user.addPublishedTopic(this);
    }

    public void setCategory(Category category) {
        if (this.category != null) {
            this.category.removeTopic(this);
        }
        this.category = category;
        category.addTopic(this);
    }

    public void addHide(HiddenTopic hiddenTopic) {
        this.hides.add(hiddenTopic);
    }

    public void addVote(Vote vote) {
        this.votes.add(vote);
    }
}
