package mk.poplaki.domain;

import mk.poplaki.domain.base.BaseDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "vote")
public class Vote extends BaseDocument {

    @Indexed
    private String complaintId;

    @Indexed
    private String userId;

    private boolean upvote;
}