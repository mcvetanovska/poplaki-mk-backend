package mk.poplaki.controller;

import mk.poplaki.dto.complaint.ComplaintResponse;
import mk.poplaki.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ComplaintService complaintService;

    @GetMapping("complaints/pageable")
    public Page<ComplaintResponse> getMineComplaintsPaginated(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "createdOn") String sortBy, @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {
        return complaintService.getMineComplaintsPaginated(page, size, sortBy, sortDirection);
    }

    @PutMapping("complaints/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void resolveComplaint(@PathVariable String id) {
        complaintService.resolveComplaint(id);
    }
}
