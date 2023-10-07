package site._60jong.jdbc.querydsl.domain.hide;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QHiddenTopicId is a Querydsl query type for HiddenTopicId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QHiddenTopicId extends BeanPath<HiddenTopicId> {

    private static final long serialVersionUID = 1781027674L;

    public static final QHiddenTopicId hiddenTopicId = new QHiddenTopicId("hiddenTopicId");

    public final NumberPath<Long> topicId = createNumber("topicId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QHiddenTopicId(String variable) {
        super(HiddenTopicId.class, forVariable(variable));
    }

    public QHiddenTopicId(Path<? extends HiddenTopicId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHiddenTopicId(PathMetadata metadata) {
        super(HiddenTopicId.class, metadata);
    }

}

