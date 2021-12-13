package com.telenor.products.dao.jpa;

import com.telenor.products.domain.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for db operations
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll(Specification<Product> specification);
}
