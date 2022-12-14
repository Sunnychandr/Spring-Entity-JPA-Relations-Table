package com.domain.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domain.dto.ResponseData;
import com.domain.models.entities.Product;
import com.domain.services.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    // inject product service
    @Autowired
    private ProductService productService;

    @PostMapping
    // @Valid untuk memastikn bahwa entity class product perlu divalidasi kalo tiak valid maka akan menggenerate objek error
    public ResponseEntity<ResponseData<Product>> create(@Valid @RequestBody Product product, Errors errors) {
        
        // mengecek validasi
        ResponseData <Product> responseData = new ResponseData<>();

        if(errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()) {       
                // masukkan pesan kesalahan tadi ke message object response data
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        responseData.setPayload(productService.save(product)); 
        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public Iterable<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    // @RequestMapping("/api/products/id")
    public Product findOne(@PathVariable("id") Long id) {
        return productService.findOne(id);
    }

    @PutMapping
    public ResponseEntity<ResponseData<Product>> update(@Valid @RequestBody Product product, Errors errors) {
        ResponseData <Product> responseData = new ResponseData<>();

        if(errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()) {       
                // masukkan pesan kesalahan tadi ke message object response data
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        responseData.setPayload(productService.save(product)); 
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/{id}")
    public Product removeOne(@PathVariable("id") Long id) {
        productService.removeOne(id);
        return null;
    }
    
}
