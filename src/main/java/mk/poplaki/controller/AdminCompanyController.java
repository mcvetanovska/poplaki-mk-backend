package mk.poplaki.controller;

import mk.poplaki.domain.CompanyStatus;
import mk.poplaki.dto.company.CompanyRequest;
import mk.poplaki.dto.company.CompanyResponse;
import mk.poplaki.service.AdminCompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/admin/companies")
@RequiredArgsConstructor
public class AdminCompanyController {

    private final AdminCompanyService companyService;

    @GetMapping("pageable")
    public Page<CompanyResponse> getCompaniesPaginated(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) CompanyStatus status, @RequestParam(defaultValue = "order") String sortBy, @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {
        return companyService.getCompaniesPaginated(page, size, status, sortBy, sortDirection);
    }

    @PostMapping
    public void addCompanies(@RequestBody List<@Valid CompanyRequest> data) {
        companyService.addCompanies(data);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateCompanyStatus(@PathVariable String id, @RequestParam CompanyStatus status) {
        companyService.updateCompanyStatus(id, status);
    }
}
