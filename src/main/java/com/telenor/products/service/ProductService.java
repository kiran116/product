package com.telenor.products.service;

import com.telenor.products.dao.jpa.ProductRepository;
import com.telenor.products.dao.jpa.ProductSpecs;
import com.telenor.products.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Service for Product API
 */
@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public ProductService() {
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProduct(long id) {
        return productRepository.findOne(id);
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.delete(id);
    }

    public List<Product> getAllProducts(String type, String minPrice, String maxPrice, String city, String property, String propertyColor, String propertyGbLimitMin, String propertyGbLimitMax) {

        return productRepository.findAll(ProductSpecs.getProductsByFilter(type, minPrice, maxPrice, city, property, propertyColor, propertyGbLimitMin, propertyGbLimitMax));

    }
}
