package com.genspark.productManager.service;

import com.genspark.productManager.entity.Product;
import com.genspark.productManager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repo;

    @Override
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    @Override
    public Product saveProduct(Product product) {
        return repo.save(product);
    }

    @Override
    public Product getProductById(int id) {
        Optional<Product> optProd = repo.findById(id);
        Product prod;
        if (optProd.isPresent()) {
            prod = optProd.get();
        }
        else {
            throw new RuntimeException("Product not found for ID : " + id);
        }
        return prod;
    }

    @Override
    public void deleteProductById(int id) {
        repo.deleteById(id);
    }

//new method with like
    public List<Product> getProductStartingWith(String str) {
        System.out.println("In Starting with call");
        return repo.getProductStartingWith(str);

    }
}
