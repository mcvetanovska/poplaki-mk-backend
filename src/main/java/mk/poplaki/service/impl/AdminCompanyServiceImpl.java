package mk.poplaki.service.impl;

import mk.poplaki.config.exception.RecordNotFoundException;
import mk.poplaki.domain.Company;
import mk.poplaki.domain.CompanyStatus;
import mk.poplaki.dto.company.CompanyRequest;
import mk.poplaki.dto.company.CompanyResponse;
import mk.poplaki.repository.CompanyRepository;
import mk.poplaki.service.AdminCompanyService;
import mk.poplaki.service.MapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCompanyServiceImpl implements AdminCompanyService {

    private final MapperService mapperService;
    private final CompanyRepository companyRepository;

    @Override
    public Page<CompanyResponse> getCompaniesPaginated(int page, int size, CompanyStatus status, String sortBy, Sort.Direction sortDirection) {
        if (status != null) {
            return companyRepository.findAllByStatus(status, PageRequest.of(page, size, Sort.by(sortDirection, sortBy)))
                    .map(mapperService::mapToCompanyResponse);
        }
        return companyRepository.findAll(PageRequest.of(page, size, Sort.by(sortDirection, sortBy)))
                .map(mapperService::mapToCompanyResponse);
    }

    @Override
    public void addCompanies(List<CompanyRequest> data) {
        List<Company> companies = data.stream().map(this::mapToCompany).toList();
        companyRepository.saveAll(companies);
    }

    @Override
    public void updateCompanyStatus(String id, CompanyStatus status) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Company not found"));
        company.setStatus(status);
        companyRepository.save(company);
    }

    private Company mapToCompany(CompanyRequest companyRequest) {
        Company company = new Company();
        company.setName(companyRequest.getName());
        company.setStatus(CompanyStatus.APPROVED);
        return company;
    }
}
