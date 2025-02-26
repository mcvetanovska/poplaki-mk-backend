package mk.poplaki.service.impl;

import mk.poplaki.domain.Company;
import mk.poplaki.domain.CompanyStatus;
import mk.poplaki.domain.ComplaintStatusType;
import mk.poplaki.dto.company.CompanyResponse;
import mk.poplaki.repository.CompanyRepository;
import mk.poplaki.repository.ComplaintRepository;
import mk.poplaki.service.CompanyService;
import mk.poplaki.service.MapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ComplaintRepository complaintRepository;
    private final MapperService mapperService;

    public Page<CompanyResponse> getCompaniesPaginated(int page, int size, String sortBy, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Company> companyResponses = companyRepository.findAllByStatus(CompanyStatus.APPROVED, pageRequest);

        List<CompanyResponse> complaintResponses = companyResponses.getContent().stream()
                .map(company -> mapperService.mapToCompanyResponse(company,
                        complaintRepository.countByCompanyIdAndStatusTypeIn(company.getId(), List.of(ComplaintStatusType.RESOLVED)),
                        complaintRepository.countByCompanyIdAndStatusTypeIn(company.getId(), List.of(ComplaintStatusType.ACCEPTED, ComplaintStatusType.RESOLVED))))
                .toList();

        return new PageImpl<>(complaintResponses, PageRequest.of(page, size), companyResponses.getTotalElements());
    }

    @Override
    public long getCompaniesCount() {
        return companyRepository.countByStatus(CompanyStatus.APPROVED);
    }

    @Override
    public CompanyResponse getCompanyById(String id) {
        return companyRepository.findById(id)
                .map(mapperService::mapToCompanyResponse)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));
    }

    @Override
    public Page<CompanyResponse> searchCompanies(int page, int size, String name) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdOn"));
        return companyRepository.findByNameContainingIgnoreCaseAndStatus(name, CompanyStatus.APPROVED, pageRequest)
                .map(mapperService::mapToCompanyResponse);
    }
}