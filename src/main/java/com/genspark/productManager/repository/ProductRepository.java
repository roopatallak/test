package com.genspark.productManager.repository;

import com.genspark.productManager.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select p from products p")
    List<Product> getAllProducts();

    @Query("select p from products p where p.productName like :pattern%")
    List<Product> getProductStartingWith(@Param("pattern") String str);
}
