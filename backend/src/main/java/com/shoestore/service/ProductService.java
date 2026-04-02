package com.shoestore.service;

import com.shoestore.dto.response.ApiResponse;
import com.shoestore.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ApiResponse<Page<Product>> getAllProducts(int page, int size, String sortBy, String sortDir);
    ApiResponse<Page<Product>> getFilteredProducts(String name, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, Boolean inStock, int page, int size, String sortBy, String sortDir);
    ApiResponse<Product> getProductById(Long id);
    ApiResponse<List<Product>> getProductsByCategory(Long categoryId);
    ApiResponse<Product> saveProduct(Product product, MultipartFile image);
    ApiResponse<Product> updateProduct(Long id, Product product, MultipartFile image);
    ApiResponse<String> deleteProduct(Long id);
}
