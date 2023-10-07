package site._60jong.jdbc.querydsl.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 848354289L;

    public static final QUser user = new QUser("user");

    public final StringPath email = createString("email");

    public final ListPath<site._60jong.jdbc.querydsl.domain.hide.HiddenTopic, site._60jong.jdbc.querydsl.domain.hide.QHiddenTopic> hiddenTopics = this.<site._60jong.jdbc.querydsl.domain.hide.HiddenTopic, site._60jong.jdbc.querydsl.domain.hide.QHiddenTopic>createList("hiddenTopics", site._60jong.jdbc.querydsl.domain.hide.HiddenTopic.class, site._60jong.jdbc.querydsl.domain.hide.QHiddenTopic.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<Topic, QTopic> publishedTopics = this.<Topic, QTopic>createList("publishedTopics", Topic.class, QTopic.class, PathInits.DIRECT2);

    public final ListPath<site._60jong.jdbc.querydsl.domain.vote.Vote, site._60jong.jdbc.querydsl.domain.vote.QVote> votes = this.<site._60jong.jdbc.querydsl.domain.vote.Vote, site._60jong.jdbc.querydsl.domain.vote.QVote>createList("votes", site._60jong.jdbc.querydsl.domain.vote.Vote.class, site._60jong.jdbc.querydsl.domain.vote.QVote.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

