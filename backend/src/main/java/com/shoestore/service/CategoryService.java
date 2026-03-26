package com.shoestore.service;

import com.shoestore.dto.response.ApiResponse;
import com.shoestore.entity.Category;
import java.util.List;

public interface CategoryService {
    ApiResponse<List<Category>> getAllCategories();
    ApiResponse<Category> getCategoryById(Long id);
    ApiResponse<Category> saveCategory(Category category);
    ApiResponse<Category> updateCategory(Long id, Category category);
    ApiResponse<String> deleteCategory(Long id);
}
