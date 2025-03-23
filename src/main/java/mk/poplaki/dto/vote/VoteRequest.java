package mk.poplaki.dto.vote;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteRequest {
    @NotBlank
    private String complaintId;

    private boolean upvote;
}