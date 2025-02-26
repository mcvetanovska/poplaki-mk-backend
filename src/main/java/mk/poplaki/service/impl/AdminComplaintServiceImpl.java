package mk.poplaki.service.impl;

import mk.poplaki.config.exception.RecordNotFoundException;
import mk.poplaki.domain.Company;
import mk.poplaki.domain.Complaint;
import mk.poplaki.domain.ComplaintStatusType;
import mk.poplaki.dto.complaint.ComplaintResponse;
import mk.poplaki.repository.CompanyRepository;
import mk.poplaki.repository.ComplaintRepository;
import mk.poplaki.service.AdminComplaintService;
import mk.poplaki.service.MapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminComplaintServiceImpl implements AdminComplaintService {

    private final MapperService mapperService;
    private final ComplaintRepository complaintRepository;
    private final CompanyRepository companyRepository;

    @Override
    public Page<ComplaintResponse> getComplaintsPaginated(int page, int size, String sortBy, Sort.Direction sortDirection, ComplaintStatusType type) {
        Sort sort = Sort.by(sortDirection, sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        if (type != null) {
            Page<Complaint> complaints = complaintRepository.findAllByStatusType(type, pageRequest);
            List<String> companyIds = complaints.getContent().stream().map(Complaint::getCompanyId).toList();

            List<Company> companies = companyRepository.findByIdIn(companyIds);

            return complaints.map(complaint -> mapperService.mapToComplaintResponse(complaint, companies.stream()
                    .filter(company -> company.getId().equals(complaint.getCompanyId()))
                    .findFirst().orElse(null)));
        }

        Page<Complaint> complaints = complaintRepository.findAll(pageRequest);
        List<String> companyIds = complaints.getContent().stream().map(Complaint::getCompanyId).toList();

        List<Company> companies = companyRepository.findByIdIn(companyIds);

        return complaints.map(complaint -> mapperService.mapToComplaintResponse(complaint, companies.stream()
                .filter(company -> company.getId().equals(complaint.getCompanyId()))
                .findFirst().orElse(null)));
    }

    @Override
    public void updateComplaintStatus(String id, ComplaintStatusType type) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Complaint not found"));

        complaint.setStatusType(type);
        complaintRepository.save(complaint);
    }
}
