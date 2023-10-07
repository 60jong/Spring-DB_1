package site._60jong.jdbc.querydsl.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTopic is a Querydsl query type for Topic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTopic extends EntityPathBase<Topic> {

    private static final long serialVersionUID = 528146889L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTopic topic = new QTopic("topic");

    public final QCategory category;

    public final ListPath<site._60jong.jdbc.querydsl.domain.hide.HiddenTopic, site._60jong.jdbc.querydsl.domain.hide.QHiddenTopic> hides = this.<site._60jong.jdbc.querydsl.domain.hide.HiddenTopic, site._60jong.jdbc.querydsl.domain.hide.QHiddenTopic>createList("hides", site._60jong.jdbc.querydsl.domain.hide.HiddenTopic.class, site._60jong.jdbc.querydsl.domain.hide.QHiddenTopic.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser publishUser;

    public final StringPath title = createString("title");

    public final ListPath<site._60jong.jdbc.querydsl.domain.vote.Vote, site._60jong.jdbc.querydsl.domain.vote.QVote> votes = this.<site._60jong.jdbc.querydsl.domain.vote.Vote, site._60jong.jdbc.querydsl.domain.vote.QVote>createList("votes", site._60jong.jdbc.querydsl.domain.vote.Vote.class, site._60jong.jdbc.querydsl.domain.vote.QVote.class, PathInits.DIRECT2);

    public QTopic(String variable) {
        this(Topic.class, forVariable(variable), INITS);
    }

    public QTopic(Path<? extends Topic> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTopic(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTopic(PathMetadata metadata, PathInits inits) {
        this(Topic.class, metadata, inits);
    }

    public QTopic(Class<? extends Topic> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.publishUser = inits.isInitialized("publishUser") ? new QUser(forProperty("publishUser")) : null;
    }

}

