package mk.poplaki.controller;

import mk.poplaki.dto.company.CompanyResponse;
import mk.poplaki.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("pageable")
    public Page<CompanyResponse> getCompaniesPaginated(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "order") String sortBy, @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {
        return companyService.getCompaniesPaginated(page, size, sortBy, sortDirection);
    }

    @GetMapping("count")
    public long getCompaniesCount() {
        return companyService.getCompaniesCount();
    }

    @GetMapping("{id}")
    public CompanyResponse getCompanyById(@PathVariable String id) {
        return companyService.getCompanyById(id);
    }

    @GetMapping("search")
    public Page<CompanyResponse> searchCompanies(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(required = false) String name) {
        return companyService.searchCompanies(page, size, name);
    }
}