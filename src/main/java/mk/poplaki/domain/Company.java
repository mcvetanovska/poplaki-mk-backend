package mk.poplaki.domain;

import mk.poplaki.domain.base.BaseDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@Document (collection = "company")
public class Company extends BaseDocument {

    private String name;
    private String logo;
    private CompanyStatus status;
    private boolean order;

}
