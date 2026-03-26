package com.shoestore.controller;

import com.shoestore.dto.response.ApiResponse;
import com.shoestore.entity.Category;
import com.shoestore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable Long id) {
        ApiResponse<Category> response = categoryService.getCategoryById(id);
        if (response.getStatus() == 200) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(404).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Category>> createCategory(@RequestBody Category category) {
        ApiResponse<Category> response = categoryService.saveCategory(category);
        if (response.getStatus() == 200) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        ApiResponse<Category> response = categoryService.updateCategory(id, category);
        if (response.getStatus() == 200) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCategory(@PathVariable Long id) {
        ApiResponse<String> response = categoryService.deleteCategory(id);
        if (response.getStatus() == 200) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
