package com.mysite.csJpa.category.dto;

import com.mysite.csJpa.category.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryResponse {
    private Integer id;
    private String categoryName;

    public CategoryResponse(Category entity) {
        this.id = entity.getId();
        this.categoryName = entity.getCategoryName();
    }

    public Category toEntity(Integer id) {
        return Category.builder()
                .id(id)
                .categoryName(categoryName)
                .build();
    }
}
