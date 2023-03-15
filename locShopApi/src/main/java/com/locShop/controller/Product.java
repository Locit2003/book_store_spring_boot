package com.locShop.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import com.locShop.model.ProductEntity;
import com.locShop.service.CategoryService;
import com.locShop.service.ProductService;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/product")
public class Product {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping(value = "/add")
	public String insertProduct(@Valid @RequestBody ProductEntity pro) {
		if(productService.save(pro)!=null) {
			return "thêm mới thành công";
			
		}else {
			return "thêm mới thất bại";
		}
	}
	
	@GetMapping(value = "/details")
	public Optional<ProductEntity> findById(@RequestParam("id") Long id) {
		return productService.findById(id);
	}
	
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
	        BindingResult bindingResult = ex.getBindingResult();
	        List<String> errors = bindingResult.getAllErrors().stream()
	                .map(ObjectError::getDefaultMessage)
	                .collect(Collectors.toList());
	        return ResponseEntity.badRequest().body(String.join(", ", errors));
	    }
}
