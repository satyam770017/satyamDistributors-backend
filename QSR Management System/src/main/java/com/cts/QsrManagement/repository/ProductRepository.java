package com.cts.QsrManagement.repository;

import com.cts.QsrManagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>
{
    @Query("SELECT p FROM Product p WHERE p.description LIKE %:description%")
    List<Product> findProductsBasedOnDescription(String description);

    @Query("SELECT p FROM Product p WHERE p.category LIKE :category%")
    List<Product> findProductsBasedOnCategory(String category);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    List<Product> findProductsBasedOnName(String name);

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2")
    List<Product> findProductsBasedOnPriceRange(double price1, double price2);
}
