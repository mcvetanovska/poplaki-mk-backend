package mk.poplaki.domain.base;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.time.Instant;

@Data
public class BaseDocument {
    @Id
    private String id;

    @Version
    private Long version;

    @CreatedDate
    private Instant createdOn;

    @LastModifiedDate
    private Instant modifiedOn;
}
