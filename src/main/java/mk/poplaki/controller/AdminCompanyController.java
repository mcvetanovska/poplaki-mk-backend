package mk.poplaki.controller;

import mk.poplaki.domain.Company;
import mk.poplaki.domain.CompanyStatus;
import mk.poplaki.dto.company.CompanyRequest;
import mk.poplaki.dto.company.CompanyResponse;
import mk.poplaki.service.AdminCompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/admin/companies")
@RequiredArgsConstructor
public class AdminCompanyController {

    private static final String UPLOAD_DIR = "./uploads/logos/";
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

    @PutMapping("/{id}/logo")
    public ResponseEntity<String> updateCompanyLogo(@PathVariable String id,
                                                    @RequestParam("logo") MultipartFile logo) throws IOException {
        Company company = companyService.getCompanyById(id);
        if (company == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found");
        }

        String fileName = UUID.randomUUID() + "_" + logo.getOriginalFilename();
        Path uploadDirPath = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();
        if (Files.exists(uploadDirPath)) {
            return ResponseEntity.status(HttpStatus.OK).body(fileName);
        }
        Files.copy(logo.getInputStream(), uploadDirPath, StandardCopyOption.REPLACE_EXISTING);

        company.setLogo(fileName);
        companyService.updateCompany(company);

        return ResponseEntity.status(HttpStatus.OK).body(fileName);
    }
}