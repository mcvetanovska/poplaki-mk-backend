package mk.poplaki.dto.complaint;

import mk.poplaki.domain.ComplaintStatusType;
import mk.poplaki.dto.company.CompanyResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
public class ComplaintResponse {
    private String id;
    private String title;
    private String description;
    private String userId;
    private String videoUrl;
    private List<String> imageUrls;
    private ComplaintStatusType statusType;
    private Instant resolvedTime;
    private Instant createdOn;
    private Instant modifiedOn;
    private CompanyResponse company;
}
