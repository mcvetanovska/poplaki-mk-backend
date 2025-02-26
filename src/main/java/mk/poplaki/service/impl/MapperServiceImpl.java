package mk.poplaki.service.impl;

import mk.poplaki.domain.Company;
import mk.poplaki.domain.Complaint;
import mk.poplaki.dto.company.CompanyResponse;
import mk.poplaki.dto.complaint.ComplaintResponse;
import mk.poplaki.service.MapperService;
import org.springframework.stereotype.Service;

@Service
public class MapperServiceImpl implements MapperService {

    @Override
    public ComplaintResponse mapToComplaintResponse(Complaint complaint, Company company) {
        ComplaintResponse complaintResponse = mapToComplaintResponse(complaint);
        complaintResponse.setCompany(mapToCompanyResponse(company));
        return complaintResponse;
    }

    @Override
    public ComplaintResponse mapToComplaintResponse(Complaint complaint) {
        ComplaintResponse complaintResponse = new ComplaintResponse();
        complaintResponse.setId(complaint.getId());
        complaintResponse.setTitle(complaint.getTitle());
        complaintResponse.setDescription(complaint.getDescription());
        complaintResponse.setUserId(complaint.getUserId());
        complaintResponse.setVideoUrl(complaint.getVideoUrl());
        complaintResponse.setImageUrls(complaint.getImageUrls());
        complaintResponse.setStatusType(complaint.getStatusType());
        complaintResponse.setResolvedTime(complaint.getResolvedTime());
        complaintResponse.setCreatedOn(complaint.getCreatedOn());
        complaintResponse.setModifiedOn(complaint.getModifiedOn());
        return complaintResponse;
    }

    @Override
    public CompanyResponse mapToCompanyResponse(Company company) {
        return mapToCompanyResponse(company, 0L,0L);
    }

    @Override
    public CompanyResponse mapToCompanyResponse(Company company, Long allResolved, Long allComplaints) {
        if(company == null){
            return null;
        }

        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setId(company.getId());
        companyResponse.setName(company.getName());
        companyResponse.setLogo(company.getLogo());
        companyResponse.setTotalResolvedComplaints(allResolved);
        companyResponse.setTotalComplaints(allComplaints);
        companyResponse.setOrder(company.isOrder());
        return companyResponse;
    }
}
