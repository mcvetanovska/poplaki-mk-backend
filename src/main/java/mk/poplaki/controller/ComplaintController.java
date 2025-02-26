package mk.poplaki.controller;

import mk.poplaki.dto.complaint.ComplaintRequest;
import mk.poplaki.dto.complaint.ComplaintResponse;
import mk.poplaki.service.ComplaintService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    @GetMapping("pageable")
    public Page<ComplaintResponse> getComplaintsPaginated(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "createdOn") String sortBy, @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {
        return complaintService.getComplaintsPaginated(page, size, sortBy, sortDirection);
    }

    @GetMapping("pageable/company/{companyId}")
    public Page<ComplaintResponse> getComplaintsByCompanyIdPaginated(@PathVariable String companyId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "createdOn") String sortBy, @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {
        return complaintService.getComplaintsByCompanyIdPaginated(page, size, sortBy, sortDirection, companyId);
    }

    @PostMapping
    public void addComplaint(@Valid @RequestBody ComplaintRequest data) {
        complaintService.addComplaint(data);
    }

    @GetMapping("count")
    public long getComplaintsCount() {
        return complaintService.getComplaintsCount();
    }

    @GetMapping("{id}")
    public ComplaintResponse getComplaintById(@PathVariable String id) {
        return complaintService.getComplaintById(id);
    }

    @GetMapping("search")
    public Page<ComplaintResponse> searchComplaints(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam String title) {
        return complaintService.searchComplaints(page, size, title);
    }

}