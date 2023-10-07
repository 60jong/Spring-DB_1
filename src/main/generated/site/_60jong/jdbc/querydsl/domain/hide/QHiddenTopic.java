package site._60jong.jdbc.querydsl.domain.hide;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHiddenTopic is a Querydsl query type for HiddenTopic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHiddenTopic extends EntityPathBase<HiddenTopic> {

    private static final long serialVersionUID = 1235371487L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHiddenTopic hiddenTopic = new QHiddenTopic("hiddenTopic");

    public final QHiddenTopicId id;

    public final site._60jong.jdbc.querydsl.domain.QTopic topic;

    public final site._60jong.jdbc.querydsl.domain.QUser user;

    public QHiddenTopic(String variable) {
        this(HiddenTopic.class, forVariable(variable), INITS);
    }

    public QHiddenTopic(Path<? extends HiddenTopic> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHiddenTopic(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHiddenTopic(PathMetadata metadata, PathInits inits) {
        this(HiddenTopic.class, metadata, inits);
    }

    public QHiddenTopic(Class<? extends HiddenTopic> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QHiddenTopicId(forProperty("id")) : null;
        this.topic = inits.isInitialized("topic") ? new site._60jong.jdbc.querydsl.domain.QTopic(forProperty("topic"), inits.get("topic")) : null;
        this.user = inits.isInitialized("user") ? new site._60jong.jdbc.querydsl.domain.QUser(forProperty("user")) : null;
    }

}

