package site._60jong.jdbc.querydsl.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Topic> topics = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public void removeTopic(Topic topic) {
        this.topics.remove(topic);
    }

    public void addTopic(Topic topic) {
        this.topics.add(topic);
    }
}
