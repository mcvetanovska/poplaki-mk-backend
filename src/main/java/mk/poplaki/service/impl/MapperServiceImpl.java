package mk.poplaki.service.impl;

import mk.poplaki.domain.Comment;
import mk.poplaki.domain.Company;
import mk.poplaki.domain.Complaint;
import mk.poplaki.dto.comment.CommentResponse;
import mk.poplaki.dto.company.CompanyResponse;
import mk.poplaki.dto.complaint.ComplaintResponse;
import mk.poplaki.repository.UserRepository;
import mk.poplaki.service.MapperService;
import org.springframework.stereotype.Service;

@Service
public class MapperServiceImpl implements MapperService {

    private final UserRepository userRepository;

    public MapperServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ComplaintResponse mapToComplaintResponse(Complaint complaint, Company company) {
        ComplaintResponse complaintResponse = mapToComplaintResponse(complaint);
        complaintResponse.setCompany(mapToCompanyResponse(company));
        return complaintResponse;
    }

    @Override
    public ComplaintResponse mapToComplaintResponse(Complaint complaint) {
        ComplaintResponse complaintResponse = new ComplaintResponse();
        complaintResponse.setId(complaint.getId());
        complaintResponse.setTitle(complaint.getTitle());
        complaintResponse.setDescription(complaint.getDescription());
        complaintResponse.setUserId(complaint.getUserId());
        complaintResponse.setVideoUrl(complaint.getVideoUrl());
        complaintResponse.setImageUrls(complaint.getImageUrls());
        complaintResponse.setStatusType(complaint.getStatusType());
        complaintResponse.setResolvedTime(complaint.getResolvedTime());
        complaintResponse.setCreatedOn(complaint.getCreatedOn());
        complaintResponse.setModifiedOn(complaint.getModifiedOn());
        return complaintResponse;
    }

    @Override
    public CompanyResponse mapToCompanyResponse(Company company) {
        return mapToCompanyResponse(company, 0L,0L);
    }

    @Override
    public CompanyResponse mapToCompanyResponse(Company company, Long allResolved, Long allComplaints) {
        if(company == null){
            return null;
        }

        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setId(company.getId());
        companyResponse.setName(company.getName());
        companyResponse.setLogo(company.getLogo());
        companyResponse.setTotalResolvedComplaints(allResolved);
        companyResponse.setTotalComplaints(allComplaints);
        companyResponse.setOrder(company.isOrder());
        return companyResponse;
    }

    @Override
    public CommentResponse mapToCommentResponse(Comment comment) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setId(comment.getId());
        commentResponse.setComplaintId(comment.getComplaintId());
        commentResponse.setUserId(comment.getUserId());
        commentResponse.setText(comment.getText());
        commentResponse.setCreatedOn(comment.getCreatedOn());

        String username = userRepository.findById(comment.getUserId())
                .map(user -> user.getNickname())
                .orElse("Unknown");
        commentResponse.setNickname(username);

        return commentResponse;
    }
}
