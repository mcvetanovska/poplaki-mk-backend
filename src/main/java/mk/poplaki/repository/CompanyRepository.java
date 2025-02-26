package mk.poplaki.repository;

import mk.poplaki.domain.Company;
import mk.poplaki.domain.CompanyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {

    List<Company> findByIdIn(List<String> companyIds);
    Page<Company> findAllByStatus(CompanyStatus status, Pageable pageable);
    Page<Company> findByNameContainingIgnoreCaseAndStatus(String name, CompanyStatus status, Pageable pageable);
    long countByStatus(CompanyStatus status);
}