package mk.poplaki.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class CommentResponse {
    private String id;
    private String complaintId;
    private String userId;
    private String nickname;
    private String text;
    private Instant createdOn;
}