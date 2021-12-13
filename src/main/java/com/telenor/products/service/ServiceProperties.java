package com.telenor.products.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/*
 * inject service specific properties
 */
@ConfigurationProperties(prefix = "product.service", ignoreUnknownFields = false)
@Component
public class ServiceProperties {

	private String name = "Empty";

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
