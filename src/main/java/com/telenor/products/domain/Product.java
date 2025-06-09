package com.telenor.products.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * a simple domain entity doubling as a DTO
 */
@Entity
@Table(name = "product")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Product {

    @Id
    @GeneratedValue()
    private long id;

    @Column(nullable = false)
    private String productType;

    @Column()
    private String productProperty;

    @Column()
    private String propertyColor;

    @Column()
    private Integer propertyGbLimit;

    @Column()
    String storeAddress;

    @Column()
    private int price;

    public Product() {
    }

    public Product(String productType, String productProperty, int price) {
        this.productType = productType;
        this.productProperty = productProperty;
        this.price = price;
    }

    public long getId() {
        return this.id;
    }
    // for tests ONLY
    public void setId(long id) {
        this.id = id;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductProperty() {
        return productProperty;
    }

    public void setProductProperty(String productProperty) {
        this.productProperty = productProperty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getPropertyColor() {
        return propertyColor;
    }

    public void setPropertyColor(String propertyColor) {
        this.propertyColor = propertyColor;
    }

    public Integer getPropertyGbLimit() {
        return propertyGbLimit;
    }

    public void setPropertyGbLimit(Integer propertyGbLimit) {
        this.propertyGbLimit = propertyGbLimit;
    }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productType='" + productType + '\'' +
                ", productProperty='" + productProperty + '\'' +
                ", propertyColor='" + propertyColor + '\'' +
                ", propertyGbLimit='" + propertyGbLimit + '\'' +
                ", storeAddress='" + storeAddress + '\'' +
                ", price=" + price +
                '}';
    }
}
