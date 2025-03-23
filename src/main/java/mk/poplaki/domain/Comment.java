package mk.poplaki.domain;

import mk.poplaki.domain.base.BaseDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "comment")
public class Comment extends BaseDocument {

    @Indexed
    private String complaintId;

    @Indexed
    private String userId;
    private String username;

    private String text;
}