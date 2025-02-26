package mk.poplaki.repository;

import mk.poplaki.domain.Complaint;
import mk.poplaki.domain.ComplaintStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepository extends MongoRepository<Complaint, String> {

    Page<Complaint> findAllByStatusType(ComplaintStatusType statusType,Pageable pageable);

    Page<Complaint> findByTitleContainingIgnoreCaseAndStatusType(String title, ComplaintStatusType type, Pageable pageable);

    long countByStatusTypeIn(List<ComplaintStatusType> statusTypes);

    long countByCompanyIdAndStatusTypeIn(String companyId, List<ComplaintStatusType> statusTypes);

    Page<Complaint> findByCompanyIdAndStatusType(String companyId, ComplaintStatusType statusType, Pageable pageable);

    Page<Complaint> findByUserId(String userId, Pageable pageable);

    Optional<Complaint> findByIdAndUserId(String id, String userId);
}