package com.telenor.products.dao.jpa;

import com.telenor.products.domain.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.util.StringUtils;


public class ProductSpecs {


    public static Specification<Product> getProductsByFilter(String type, String minPrice, String maxPrice, String city, String property, String propertyColor, String propertyGbLimitMin, String propertyGbLimitMax) {

        Specification<Product> specBuilder = null;
        if (!StringUtils.isEmpty(type)) {
            specBuilder = addSpec(getProductsByType(type), specBuilder);
        }
        if (!StringUtils.isEmpty(minPrice)) {
            specBuilder = addSpec(getProductsByMinPrice(minPrice), specBuilder);
        }
        if (!StringUtils.isEmpty(maxPrice)) {
            specBuilder = addSpec(getProductsByMaxPrice(maxPrice), specBuilder);
        }
        if (!StringUtils.isEmpty(city)) {
            specBuilder = addSpec(getProductsByCity(city), specBuilder);
        }
        if (!StringUtils.isEmpty(property)) {
            specBuilder = addSpec(getProductsByProperty(property), specBuilder);
        }
        if (!StringUtils.isEmpty(propertyColor)) {
            specBuilder = addSpec(getProductsByPropertyColor(propertyColor), specBuilder);
        }
        if (!StringUtils.isEmpty(propertyGbLimitMin)) {
            specBuilder = addSpec(getProductsByPropertyGbLimitMin(propertyGbLimitMin), specBuilder);
        }
        if (!StringUtils.isEmpty(propertyGbLimitMax)) {
            specBuilder = addSpec(getProductsByPropertyGbLimitMax(propertyGbLimitMax), specBuilder);
        }
        return specBuilder;
    }

    private static Specification<Product> addSpec(Specification<Product> spec, Specification<Product> specBuilder) {
        specBuilder = spec != null ? Specifications.where(spec).and(specBuilder) : specBuilder;
        return specBuilder;
    }

    private static Specification<Product> getProductsByType(String type) {
        return (root, criteriaQuery, cb) -> cb.equal(root.get("productType"), type);
    }

    private static Specification<Product> getProductsByMinPrice(String minPrice) {
        return (root, criteriaQuery, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    private static Specification<Product> getProductsByMaxPrice(String maxPrice) {
        return (root, criteriaQuery, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    private static Specification<Product> getProductsByCity(String city) {
        return (root, criteriaQuery, cb) -> cb.like(cb.lower(root.get("storeAddress")), "%" + city.toLowerCase() + "%");
    }

    private static Specification<Product> getProductsByProperty(String property) {
        return (root, criteriaQuery, cb) -> cb.equal(root.get("productProperty"), property);
    }

    private static Specification<Product> getProductsByPropertyColor(String propertyColor) {
        return (root, criteriaQuery, cb) -> cb.equal(root.get("propertyColor"), propertyColor);
    }

    private static Specification<Product> getProductsByPropertyGbLimitMin(String propertyGbLimitMin) {
        Integer min = Integer.valueOf(propertyGbLimitMin);
        return (root, criteriaQuery, cb) -> cb.greaterThanOrEqualTo(root.get("propertyGbLimit"), min);
    }

    private static Specification<Product> getProductsByPropertyGbLimitMax(String propertyGbLimitMax) {
        Integer max = Integer.valueOf(propertyGbLimitMax);
        return (root, criteriaQuery, cb) -> cb.lessThanOrEqualTo(root.get("propertyGbLimit"), max);
    }
}
