package com.shoestore.service.impl;

import com.shoestore.dto.response.ApiResponse;
import com.shoestore.entity.Category;
import com.shoestore.repository.CategoryRepository;
import com.shoestore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public ApiResponse<List<Category>> getAllCategories() {
        return ApiResponse.success(categoryRepository.findAll());
    }

    @Override
    public ApiResponse<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(ApiResponse::success)
                .orElse(ApiResponse.error(404, "Category not found"));
    }

    @Override
    @Transactional
    public ApiResponse<Category> saveCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            return ApiResponse.error(400, "Category name already exists");
        }
        return ApiResponse.success(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public ApiResponse<Category> updateCategory(Long id, Category category) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(category.getName());
                    existingCategory.setDescription(category.getDescription());
                    return ApiResponse.success(categoryRepository.save(existingCategory));
                })
                .orElse(ApiResponse.error(404, "Category not found"));
    }

    @Override
    @Transactional
    public ApiResponse<String> deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            return ApiResponse.error(404, "Category not found");
        }
        categoryRepository.deleteById(id);
        return ApiResponse.success("Category deleted successfully");
    }
}
