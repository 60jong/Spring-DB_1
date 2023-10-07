package site._60jong.jdbc.querydsl.domain.vote;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVote is a Querydsl query type for Vote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVote extends EntityPathBase<Vote> {

    private static final long serialVersionUID = -1152579768L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVote vote = new QVote("vote");

    public final QVoteId id;

    public final EnumPath<VoteSide> side = createEnum("side", VoteSide.class);

    public final site._60jong.jdbc.querydsl.domain.QTopic topic;

    public final site._60jong.jdbc.querydsl.domain.QUser user;

    public QVote(String variable) {
        this(Vote.class, forVariable(variable), INITS);
    }

    public QVote(Path<? extends Vote> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVote(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVote(PathMetadata metadata, PathInits inits) {
        this(Vote.class, metadata, inits);
    }

    public QVote(Class<? extends Vote> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QVoteId(forProperty("id")) : null;
        this.topic = inits.isInitialized("topic") ? new site._60jong.jdbc.querydsl.domain.QTopic(forProperty("topic"), inits.get("topic")) : null;
        this.user = inits.isInitialized("user") ? new site._60jong.jdbc.querydsl.domain.QUser(forProperty("user")) : null;
    }

}

