package com.assignment3.project.mappers;

import com.assignment3.project.dto.responses.UserResponse;
import com.assignment3.project.entities.User;
import com.assignment3.project.enums.UserRole;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-15T10:53:44+0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponse toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setAvatarPath( entity.getAvatarPath() );
        userResponse.setDocPath( entity.getDocPath() );
        userResponse.setEmail( entity.getEmail() );
        userResponse.setFullName( entity.getFullName() );
        userResponse.setId( entity.getId() );
        if ( entity.getRole() != null ) {
            userResponse.setRole( entity.getRole().name() );
        }
        userResponse.setVerified( entity.isVerified() );

        return userResponse;
    }

    @Override
    public User toEntity(UserResponse dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.avatarPath( dto.getAvatarPath() );
        user.docPath( dto.getDocPath() );
        user.email( dto.getEmail() );
        user.fullName( dto.getFullName() );
        user.id( dto.getId() );
        if ( dto.getRole() != null ) {
            user.role( Enum.valueOf( UserRole.class, dto.getRole() ) );
        }

        return user.build();
    }
}
