package com.cts.QsrManagement.controller;


import com.cts.QsrManagement.model.Product;
import com.cts.QsrManagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping("/products")
public class ProductController
{

    @Autowired
    private ProductService productService;

    @GetMapping("/allproducts")
    public ResponseEntity<List<Product>> getAllProducts()
    {
        List<Product> productList = productService.getAllProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id)
    {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product productObj)
    {
        Product product = productService.addProduct(productObj);
        return new ResponseEntity<>(product,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id)
    {
        productService.deleteProduct(id);
    }

    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product productObj)
    {
        Product product = productService.updateProduct(productObj);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @GetMapping("product/description/{descriptionName}")
    public ResponseEntity<List<Product>> getProductBasedOnDescription(@PathVariable String description)
    {
        List<Product> productList = productService.searchProductsBasedOnDescription(description);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("product/{category}")
    public ResponseEntity<List<Product>> getProductBasedOnCategory(@PathVariable String category)
    {
        List<Product> productList = productService.searchProductsBasedOnCategory(category);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("product/name/{name}")
    public ResponseEntity<List<Product>> getProductBasedOnName(@PathVariable String name)
    {
        List<Product> productList = productService.searchProductsBasedOnName(name);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }


    @GetMapping("price/{price1}/{price2}")
    public ResponseEntity<List<Product>> getProductBasedOnPriceRange(@PathVariable double price1, @PathVariable double price2)
    {
        List<Product> productList = productService.searchProductsBasedOnPriceRange(price1,price2);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }


}
