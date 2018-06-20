/**
  * Created by Saddam on 04/28/2018
  */
  
  
package com.example.service;

import com.example.model.ProductForm;
import com.example.document.Product;

import java.util.List;
import java.util.UUID;


public interface ProductService {

    List<Product> listAll();

    Product getById(UUID id);

    Product saveOrUpdate(Product product);

    void delete(UUID id);

    Product saveOrUpdateProductForm(ProductForm productForm);
}
