package com.assignment3.project.mappers;

import com.assignment3.project.dto.responses.ProjectResponse;
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
public class ProjectMapperImpl implements ProjectMapper {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ProjectResponse toDto(Project entity) {
        if ( entity == null ) {
            return null;
        }

        ProjectResponse projectResponse = new ProjectResponse();

        projectResponse.setAuthor( userMapper.toDto( entity.getAuthor() ) );
        projectResponse.setCategory( categoryMapper.toDto( entity.getCategory() ) );
        projectResponse.setCollectedAmount( entity.getCollectedAmount() );
        projectResponse.setDescription( entity.getDescription() );
        projectResponse.setId( entity.getId() );
        projectResponse.setTargetAmount( entity.getTargetAmount() );
        projectResponse.setTitle( entity.getTitle() );
        projectResponse.setVerified( entity.isVerified() );

        projectResponse.setImagePaths( mapImagePaths(entity) );

        return projectResponse;
    }
}
