package mk.poplaki.service;

import mk.poplaki.domain.ComplaintStatusType;
import mk.poplaki.dto.complaint.ComplaintResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface AdminComplaintService {
    Page<ComplaintResponse> getComplaintsPaginated(int page, int size, String sortBy, Sort.Direction sortDirection, ComplaintStatusType type);

    void updateComplaintStatus(String id, ComplaintStatusType type);

}
