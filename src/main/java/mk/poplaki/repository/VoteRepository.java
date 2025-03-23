package mk.poplaki.repository;

import mk.poplaki.domain.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends MongoRepository<Vote, String> {
    long countByComplaintIdAndUpvote(String complaintId, boolean upvote);
}