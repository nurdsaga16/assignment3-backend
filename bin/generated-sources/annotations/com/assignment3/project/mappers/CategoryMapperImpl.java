package com.assignment3.project.mappers;

import com.assignment3.project.dto.responses.CategoryResponse;
import com.assignment3.project.entities.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-15T10:53:43+0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryResponse toDto(Category entity) {
        if ( entity == null ) {
            return null;
        }

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setDescription( entity.getDescription() );
        categoryResponse.setId( entity.getId() );
        categoryResponse.setName( entity.getName() );

        return categoryResponse;
    }

    @Override
    public Category toEntity(CategoryResponse dto) {
        if ( dto == null ) {
            return null;
        }

        Category category = new Category();

        category.setDescription( dto.getDescription() );
        category.setId( dto.getId() );
        category.setName( dto.getName() );

        return category;
    }
}
