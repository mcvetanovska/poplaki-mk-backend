package mk.poplaki.service;

import mk.poplaki.domain.Company;
import mk.poplaki.domain.CompanyStatus;
import mk.poplaki.dto.company.CompanyRequest;
import mk.poplaki.dto.company.CompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface AdminCompanyService {
    Page<CompanyResponse> getCompaniesPaginated(int page, int size, CompanyStatus type, String sortBy, Sort.Direction sortDirection);
    void addCompanies(List<CompanyRequest> data);

    void updateCompanyStatus(String id, CompanyStatus status);

    Company getCompanyById(String id);

    void updateCompany(Company company);
}
