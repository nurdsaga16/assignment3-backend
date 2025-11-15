package com.assignment3.project.mappers;

import com.assignment3.project.dto.responses.DonationResponse;
import com.assignment3.project.entities.Donation;
import com.assignment3.project.entities.Project;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-15T10:53:44+0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class DonationMapperImpl implements DonationMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public DonationResponse toDto(Donation entity) {
        if ( entity == null ) {
            return null;
        }

        DonationResponse donationResponse = new DonationResponse();

        donationResponse.setProjectId( entityProjectId( entity ) );
        donationResponse.setAmount( entity.getAmount() );
        donationResponse.setCreatedAt( entity.getCreatedAt() );
        donationResponse.setDonor( userMapper.toDto( entity.getDonor() ) );
        donationResponse.setId( entity.getId() );

        return donationResponse;
    }

    private Long entityProjectId(Donation donation) {
        if ( donation == null ) {
            return null;
        }
        Project project = donation.getProject();
        if ( project == null ) {
            return null;
        }
        Long id = project.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
