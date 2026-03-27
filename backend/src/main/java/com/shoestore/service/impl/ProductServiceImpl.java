package com.shoestore.service.impl;

import com.shoestore.dto.response.ApiResponse;
import com.shoestore.entity.Category;
import com.shoestore.entity.Product;
import com.shoestore.repository.CategoryRepository;
import com.shoestore.repository.ProductRepository;
import com.shoestore.service.CloudinaryService;
import com.shoestore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public ApiResponse<List<Product>> getAllProducts() {
        return ApiResponse.success(productRepository.findAll());
    }

    @Override
    public ApiResponse<Product> getProductById(Long id) {
        return productRepository.findById(id)
                .map(ApiResponse::success)
                .orElse(ApiResponse.error(404, "Product not found"));
    }

    @Override
    public ApiResponse<List<Product>> getProductsByCategory(Long categoryId) {
        return ApiResponse.success(productRepository.findByCategoryId(categoryId));
    }

    @Override
    @Transactional
    public ApiResponse<Product> saveProduct(Product product, MultipartFile image) {
        try {
            if (image != null && !image.isEmpty()) {
                Map<String, String> uploadResult = cloudinaryService.uploadFile(image, "products");
                product.setImage(uploadResult.get("url"));
                product.setImagePublicId(uploadResult.get("public_id"));
            }
            
            // Check if category exists
            if (product.getCategory() != null && product.getCategory().getId() != null) {
                Category category = categoryRepository.findById(product.getCategory().getId())
                        .orElse(null);
                if (category == null) {
                    return ApiResponse.error(400, "Category not found");
                }
                product.setCategory(category);
            }

            return ApiResponse.success(productRepository.save(product));
        } catch (IOException e) {
            return ApiResponse.error(500, "Error uploading image: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ApiResponse<Product> updateProduct(Long id, Product product, MultipartFile image) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    try {
                        existingProduct.setName(product.getName());
                        existingProduct.setDescription(product.getDescription());
                        existingProduct.setPrice(product.getPrice());
                        existingProduct.setStock(product.getStock());
                        
                        if (image != null && !image.isEmpty()) {
                            // Delete old image if exists
                            if (existingProduct.getImagePublicId() != null) {
                                cloudinaryService.deleteFile(existingProduct.getImagePublicId());
                            }
                            
                            Map<String, String> uploadResult = cloudinaryService.uploadFile(image, "products");
                            existingProduct.setImage(uploadResult.get("url"));
                            existingProduct.setImagePublicId(uploadResult.get("public_id"));
                        }

                        if (product.getCategory() != null && product.getCategory().getId() != null) {
                            Category category = categoryRepository.findById(product.getCategory().getId())
                                    .orElse(null);
                            if (category == null) {
                                return ApiResponse.<Product>error(400, "Category not found");
                            }
                            existingProduct.setCategory(category);
                        }

                        return ApiResponse.success(productRepository.save(existingProduct));
                    } catch (IOException e) {
                        return ApiResponse.<Product>error(500, "Error uploading image: " + e.getMessage());
                    }
                })
                .orElse(ApiResponse.error(404, "Product not found"));
    }

    @Override
    @Transactional
    public ApiResponse<String> deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    try {
                        if (product.getImagePublicId() != null) {
                            cloudinaryService.deleteFile(product.getImagePublicId());
                        }
                        productRepository.delete(product);
                        return ApiResponse.success("Product deleted successfully");
                    } catch (IOException e) {
                        return ApiResponse.<String>error(500, "Error deleting image from Cloudinary: " + e.getMessage());
                    }
                })
                .orElse(ApiResponse.error(404, "Product not found"));
    }
}
