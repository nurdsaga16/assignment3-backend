package com.assignment3.project.mappers;

import com.assignment3.project.dto.responses.VolunteerEventResponse;
import com.assignment3.project.entities.VolunteerEvent;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-15T10:53:44+0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class VolunteerEventMapperImpl implements VolunteerEventMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public VolunteerEventResponse toDto(VolunteerEvent entity) {
        if ( entity == null ) {
            return null;
        }

        VolunteerEventResponse volunteerEventResponse = new VolunteerEventResponse();

        volunteerEventResponse.setCreatedAt( entity.getCreatedAt() );
        volunteerEventResponse.setDescription( entity.getDescription() );
        volunteerEventResponse.setEventDate( entity.getEventDate() );
        volunteerEventResponse.setId( entity.getId() );
        volunteerEventResponse.setLocation( entity.getLocation() );
        volunteerEventResponse.setOrganizer( userMapper.toDto( entity.getOrganizer() ) );
        volunteerEventResponse.setTitle( entity.getTitle() );
        volunteerEventResponse.setUpdatedAt( entity.getUpdatedAt() );

        volunteerEventResponse.setParticipants( mapParticipants(entity) );

        return volunteerEventResponse;
    }
}
