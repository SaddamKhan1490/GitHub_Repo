/**
  * Created by Saddam on 04/28/2018
  */
  
  
package com.example..service;

import guru.springframework.commands.ProductForm;
import guru.springframework.domain.Product;

import java.util.List;


public interface ProductService {

    List<Product> listAll();

    Product getById(Long id);

    Product saveOrUpdate(Product product);

    void delete(Long id);

    Product saveOrUpdateProductForm(ProductForm productForm);
    
}
