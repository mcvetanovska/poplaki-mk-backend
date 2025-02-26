package mk.poplaki.controller;

import mk.poplaki.domain.ComplaintStatusType;
import mk.poplaki.dto.complaint.ComplaintResponse;
import mk.poplaki.service.AdminComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/admin/complaints")
@RequiredArgsConstructor
public class AdminComplaintController {

    private final AdminComplaintService adminComplaintService;

    @GetMapping("pageable")
    public Page<ComplaintResponse> getComplaintsPaginated(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "createdOn") String sortBy, @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection, @RequestParam(required = false) ComplaintStatusType type) {
        return adminComplaintService.getComplaintsPaginated(page, size, sortBy, sortDirection, type);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateComplaint(@PathVariable String id, @RequestParam ComplaintStatusType type) {
        adminComplaintService.updateComplaintStatus(id, type);
    }
}
