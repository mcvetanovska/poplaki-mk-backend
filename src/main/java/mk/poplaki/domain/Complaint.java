package mk.poplaki.domain;

import mk.poplaki.domain.base.BaseDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "complaint")
public class Complaint extends BaseDocument {

    private String title;
    private String description;

    @Indexed
    private String userId;

    @Indexed
    private String companyId;
    private String videoUrl;
    private List<String> imageUrls;

    private ComplaintStatusType statusType = ComplaintStatusType.PENDING;
    private Instant resolvedTime;

    private List<Comment> comments;
    private int voteCount;

}
