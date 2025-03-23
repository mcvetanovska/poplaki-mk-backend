package mk.poplaki.service.impl;

import mk.poplaki.config.exception.ConflictException;
import mk.poplaki.config.exception.RecordNotFoundException;
import mk.poplaki.config.security.context.SecurityContext;
import mk.poplaki.domain.*;
import mk.poplaki.dto.comment.CommentRequest;
import mk.poplaki.dto.comment.CommentResponse;
import mk.poplaki.dto.complaint.ComplaintRequest;
import mk.poplaki.dto.complaint.ComplaintResponse;
import mk.poplaki.dto.vote.VoteRequest;
import mk.poplaki.repository.CommentRepository;
import mk.poplaki.repository.CompanyRepository;
import mk.poplaki.repository.ComplaintRepository;
import mk.poplaki.repository.VoteRepository;
import mk.poplaki.service.ComplaintService;
import mk.poplaki.service.MapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final SecurityContext securityContext;
    private final ComplaintRepository complaintRepository;
    private final CompanyRepository companyRepository;
    private final MapperService mapperService;
    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;

    @Override
    public Page<ComplaintResponse> getComplaintsPaginated(int page, int size, String sortBy, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Complaint> complaints = complaintRepository.findAllByStatusType(ComplaintStatusType.ACCEPTED, pageRequest);

        List<ComplaintResponse> complaintResponses = complaints.getContent().stream()
                .map(complaint -> mapperService.mapToComplaintResponse(complaint, companyRepository.findById(complaint.getCompanyId()).orElse(null)))
                .toList();

        return new PageImpl<>(complaintResponses, PageRequest.of(page, size), complaints.getTotalElements());

    }

    @Override
    public void addComplaint(ComplaintRequest complaintRequest) {
        if (!companyRepository.existsById(complaintRequest.getCompanyId())) {
            throw new RecordNotFoundException("Company not found");
        }

        Complaint complaint = new Complaint();
        complaint.setUserId(securityContext.getPrincipal().getUserId());
        complaint.setCompanyId(complaintRequest.getCompanyId());
        complaint.setTitle(complaintRequest.getTitle());
        complaint.setDescription(complaintRequest.getDescription());
        complaint.setVideoUrl(complaintRequest.getVideoUrl());
        complaint.setImageUrls(complaintRequest.getImageUrls());

        complaintRepository.save(complaint);
    }

    @Override
    public long getComplaintsCount() {
        return complaintRepository.countByStatusTypeIn(List.of(ComplaintStatusType.ACCEPTED, ComplaintStatusType.RESOLVED));
    }

    @Override
    public ComplaintResponse getComplaintById(String id) {
        Optional<Complaint> complaintOptional = complaintRepository.findById(id);
        if (complaintOptional.isEmpty()) {
            throw new RecordNotFoundException("Complaint not found");
        }

        Complaint complaint = complaintOptional.get();
        String companyId = complaint.getCompanyId();
        Company company = companyRepository.findById(companyId).orElse(null);

        List<CommentResponse> comments = commentRepository.findByComplaintId(id).stream()
                .map(mapperService::mapToCommentResponse)
                .toList();

        long upvotes = voteRepository.countByComplaintIdAndUpvote(id, true);
        long downvotes = voteRepository.countByComplaintIdAndUpvote(id, false);
        int voteCount = (int) (upvotes - downvotes);

        ComplaintResponse complaintResponse = mapperService.mapToComplaintResponse(complaint, company);
        complaintResponse.setComments(comments);
        complaintResponse.setVoteCount(voteCount);

        return complaintResponse;
    }

    @Override
    public Page<ComplaintResponse> searchComplaints(int page, int size, String title) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdOn"));
        Page<Complaint> complaints = complaintRepository.findByTitleContainingIgnoreCaseAndStatusType(title, ComplaintStatusType.ACCEPTED, pageRequest);

        List<ComplaintResponse> complaintResponses = complaints.getContent().stream()
                .map(complaint -> mapperService.mapToComplaintResponse(complaint, companyRepository.findById(complaint.getCompanyId()).orElse(null)))
                .toList();

        return new PageImpl<>(complaintResponses, PageRequest.of(page, size), complaints.getTotalElements());
    }

    @Override
    public Page<ComplaintResponse> getComplaintsByCompanyIdPaginated(int page, int size, String sortBy, Sort.Direction sortDirection, String companyId) {
        Sort sort = Sort.by(sortDirection, sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Complaint> complaints = complaintRepository.findByCompanyIdAndStatusType(companyId, ComplaintStatusType.ACCEPTED, pageRequest);

        List<ComplaintResponse> complaintResponses = complaints.getContent().stream()
                .map(complaint -> mapperService.mapToComplaintResponse(complaint, companyRepository.findById(complaint.getCompanyId()).orElse(null)))
                .toList();

        return new PageImpl<>(complaintResponses, PageRequest.of(page, size), complaints.getTotalElements());
    }

    @Override
    public Page<ComplaintResponse> getMineComplaintsPaginated(int page, int size, String sortBy, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Complaint> complaints = complaintRepository.findByUserId(securityContext.getPrincipal().getUserId(), pageRequest);

        List<ComplaintResponse> complaintResponses = complaints.getContent().stream()
                .map(complaint -> mapperService.mapToComplaintResponse(complaint, companyRepository.findById(complaint.getCompanyId()).orElse(null)))
                .toList();

        return new PageImpl<>(complaintResponses, PageRequest.of(page, size), complaints.getTotalElements());
    }

    @Override
    public void resolveComplaint(String id) {
        Complaint complaint = complaintRepository.findByIdAndUserId(id, securityContext.getPrincipal().getUserId())
                .orElseThrow(() -> new RecordNotFoundException("Complaint not found"));

        if (!ComplaintStatusType.ACCEPTED.equals(complaint.getStatusType())) {
            throw new ConflictException("Invalid status type");
        }

        complaint.setStatusType(ComplaintStatusType.RESOLVED);
        complaintRepository.save(complaint);
    }

    @Override
    public void addComment(CommentRequest commentRequest) {
        Complaint complaint = complaintRepository.findById(commentRequest.getComplaintId())
                .orElseThrow(() -> new RecordNotFoundException("Complaint not found"));

        Comment comment = new Comment();
        comment.setComplaintId(commentRequest.getComplaintId());
        comment.setUserId(securityContext.getPrincipal().getUserId());
        comment.setText(commentRequest.getText());

        commentRepository.save(comment);

        List<Comment> comments = commentRepository.findByComplaintId(commentRequest.getComplaintId());
        complaint.setComments(comments);
        complaintRepository.save(complaint);
    }

    @Override
    public void addVote(VoteRequest voteRequest) {
        Complaint complaint = complaintRepository.findById(voteRequest.getComplaintId())
                .orElseThrow(() -> new RecordNotFoundException("Complaint not found"));

        Vote vote = new Vote();
        vote.setComplaintId(voteRequest.getComplaintId());
        vote.setUserId(securityContext.getPrincipal().getUserId());
        vote.setUpvote(voteRequest.isUpvote());

        voteRepository.save(vote);

        long upvotes = voteRepository.countByComplaintIdAndUpvote(voteRequest.getComplaintId(), true);
        long downvotes = voteRepository.countByComplaintIdAndUpvote(voteRequest.getComplaintId(), false);

        complaint.setVoteCount((int) (upvotes - downvotes));
        complaintRepository.save(complaint);
    }
}