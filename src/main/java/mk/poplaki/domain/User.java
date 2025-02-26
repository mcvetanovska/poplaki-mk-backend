package mk.poplaki.domain;

import mk.poplaki.domain.base.BaseDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "user")
public class User extends BaseDocument {
    private String nickname;
    private String email;
    private String password;
    private String role;
}
