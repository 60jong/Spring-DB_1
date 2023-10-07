package site._60jong.jdbc.querydsl.domain.vote;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVoteId is a Querydsl query type for VoteId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QVoteId extends BeanPath<VoteId> {

    private static final long serialVersionUID = 472407683L;

    public static final QVoteId voteId = new QVoteId("voteId");

    public final NumberPath<Long> topicId = createNumber("topicId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QVoteId(String variable) {
        super(VoteId.class, forVariable(variable));
    }

    public QVoteId(Path<? extends VoteId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVoteId(PathMetadata metadata) {
        super(VoteId.class, metadata);
    }

}

