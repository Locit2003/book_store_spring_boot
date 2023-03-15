package com.locShop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.locShop.model.ProductEntity;
import com.locShop.service.ProductService;

@RestController
public class Home {

	@Autowired
	ProductService productService;

	@RequestMapping("/")
	public List<ProductEntity> homePage() {
		List<ProductEntity> products;
		try {
			products = productService.findAll();
		} catch (Exception e) {
			throw new RuntimeException("Error retrieving products", e);
		}
		return products;
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}
}
