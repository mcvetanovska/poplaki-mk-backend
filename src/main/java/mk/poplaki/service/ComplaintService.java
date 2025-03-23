package mk.poplaki.service;

import mk.poplaki.dto.comment.CommentRequest;
import mk.poplaki.dto.complaint.ComplaintRequest;
import mk.poplaki.dto.complaint.ComplaintResponse;
import mk.poplaki.dto.vote.VoteRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface ComplaintService {
    Page<ComplaintResponse> getComplaintsPaginated(int page, int size, String sortBy, Sort.Direction sortDirection);

    void addComplaint(ComplaintRequest complaintRequest);

    long getComplaintsCount();

    ComplaintResponse getComplaintById(String id);

    Page<ComplaintResponse> searchComplaints(int page, int size, String title);

    Page<ComplaintResponse> getComplaintsByCompanyIdPaginated(int page, int size, String sortBy, Sort.Direction sortDirection, String companyId);

    Page<ComplaintResponse> getMineComplaintsPaginated(int page, int size, String sortBy, Sort.Direction sortDirection);

    void resolveComplaint(String id);

    void addComment(CommentRequest commentRequest);

    void addVote(VoteRequest voteRequest);
}