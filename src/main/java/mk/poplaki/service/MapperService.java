package mk.poplaki.service;

import mk.poplaki.domain.Comment;
import mk.poplaki.domain.Company;
import mk.poplaki.domain.Complaint;
import mk.poplaki.dto.comment.CommentResponse;
import mk.poplaki.dto.company.CompanyResponse;
import mk.poplaki.dto.complaint.ComplaintResponse;

public interface MapperService {
    ComplaintResponse mapToComplaintResponse(Complaint complaint, Company company);
    ComplaintResponse mapToComplaintResponse(Complaint complaint);
    CompanyResponse mapToCompanyResponse(Company company);
    CompanyResponse mapToCompanyResponse(Company company, Long resolved, Long allComplaints);
    CommentResponse mapToCommentResponse(Comment comment);
}
