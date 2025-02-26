package mk.poplaki.dto.company;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompanyResponse {
    String id;
    String name;
    String logo;
    boolean order;

    long totalComplaints;
    long totalResolvedComplaints;
}
