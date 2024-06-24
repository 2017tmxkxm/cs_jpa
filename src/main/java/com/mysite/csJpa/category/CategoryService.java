package com.mysite.csJpa.category;

import com.mysite.csJpa.category.dto.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public List<CategoryResponse> findAll() {
        List<Category> list = categoryRepository.findAll();

        /* Stream API O */
        return list.stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());


        /* Stream API X
        List<CategoryResponse> categoryResponseList = new ArrayList<>();

        for(Category entity : list) {
            categoryResponseList.add(new CategoryResponse(entity));
        }

        return categoryResponseList;
        */
    }
}
