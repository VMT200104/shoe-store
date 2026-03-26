package com.shoestore.service;

import com.shoestore.dto.response.ApiResponse;
import com.shoestore.entity.Product;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ProductService {
    ApiResponse<List<Product>> getAllProducts();
    ApiResponse<Product> getProductById(Long id);
    ApiResponse<List<Product>> getProductsByCategory(Long categoryId);
    ApiResponse<Product> saveProduct(Product product, MultipartFile image);
    ApiResponse<Product> updateProduct(Long id, Product product, MultipartFile image);
    ApiResponse<String> deleteProduct(Long id);
}
