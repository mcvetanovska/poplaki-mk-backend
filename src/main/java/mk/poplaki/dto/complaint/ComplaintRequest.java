package mk.poplaki.dto.complaint;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ComplaintRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String companyId;

    private String videoUrl;
    private List<String> imageUrls;
}