package mk.poplaki.service;

import mk.poplaki.dto.company.CompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface CompanyService {
    Page<CompanyResponse> getCompaniesPaginated(int page, int size, String sortBy, Sort.Direction sortDirection);

    long getCompaniesCount();

    CompanyResponse getCompanyById(String id);

    Page<CompanyResponse> searchCompanies(int page, int size, String name);
}
