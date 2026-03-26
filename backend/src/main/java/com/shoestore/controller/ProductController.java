package com.shoestore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoestore.dto.response.ApiResponse;
import com.shoestore.entity.Product;
import com.shoestore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long id) {
        ApiResponse<Product> response = productService.getProductById(id);
        if (response.getStatus() == 200) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(404).body(response);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<Product>>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<Product>> createProduct(
            @RequestPart("product") String productJson,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        
        Product product = objectMapper.readValue(productJson, Product.class);
        ApiResponse<Product> response = productService.saveProduct(product, image);
        
        if (response.getStatus() == 200) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<Product>> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") String productJson,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        
        Product product = objectMapper.readValue(productJson, Product.class);
        ApiResponse<Product> response = productService.updateProduct(id, product, image);
        
        if (response.getStatus() == 200) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable Long id) {
        ApiResponse<String> response = productService.deleteProduct(id);
        if (response.getStatus() == 200) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
