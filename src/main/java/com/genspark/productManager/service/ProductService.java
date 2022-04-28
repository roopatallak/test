package com.genspark.productManager.service;

import com.genspark.productManager.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {

    List<Product> getAllProducts();
    Product saveProduct(Product product);
    Product getProductById(int id);
    void deleteProductById(int id);
    List<Product> getProductStartingWith(String str);
}
