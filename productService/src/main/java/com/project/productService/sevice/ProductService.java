package com.project.productService.sevice;

import com.project.productService.dto.ProductRequest;
import com.project.productService.dto.ProductResponse;

import java.util.List;

public interface ProductService {
     void createProduct(ProductRequest productRequest);

     List<ProductResponse> getAll();
}
