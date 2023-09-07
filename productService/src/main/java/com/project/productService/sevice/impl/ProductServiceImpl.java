package com.project.productService.sevice.impl;

import com.project.productService.dto.ProductRequest;
import com.project.productService.dto.ProductResponse;
import com.project.productService.model.Product;
import com.project.productService.repository.ProductRepo;
import com.project.productService.sevice.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepo productRepo;

    @Override
    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder().name(productRequest.getName()).description(productRequest.getDescription())
                .price(productRequest.getPrice()).build();
        productRepo.save(product);
        log.info("Product {} is Saved :",product.getId());
    }

    @Override
    public List<ProductResponse> getAll() {
        List<Product> products = productRepo.findAll();
        List<ProductResponse> productResponses =  products.stream().map(this::mapToProductReponse).collect(Collectors.toList());
        return productResponses;
    }
    ProductResponse mapToProductReponse(Product product){
        return ProductResponse.builder().id(product.getId()).name(product.getName()).description(product.getDescription()).price(product.getPrice()).
                build();
    }
}
