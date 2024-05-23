package com.cts.QsrManagement.service;

import com.cts.QsrManagement.exception.ResourceNotFoundException;
import com.cts.QsrManagement.model.Product;
import com.cts.QsrManagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService
{

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts()
    {
        List<Product> productList = productRepository.findAll();
        if(productList.isEmpty())
        {
            throw new ResourceNotFoundException("No products are present");
        }
        return productList;
    }



    public Product getProductById(Long id)
    {
        Optional<Product> product = productRepository.findById(id);
        try
        {
            return product.get();
        }
        catch (Exception e)
        {
            throw new ResourceNotFoundException("The product you are searching for does not exist");
        }
    }

    public Product addProduct(Product product)
    {
        if(product !=null)
        {
                return productRepository.save(product);
        }
        else
        {
            throw new ResourceNotFoundException("All the product details are not given");
        }


    }

    public void deleteProduct(Long id)
    {
        try
        {
            productRepository.findById(id).get();
            productRepository.deleteById(id);
        }
        catch (Exception e)
        {
            throw new ResourceNotFoundException("The product with id :"+id+" doesn't exist.");
        }
    }

    public Product updateProduct(Product product)
    {
        try
        {
            Product product1 = productRepository.findById(product.getId()).get();
            product1.setStockAvailable(product.getStockAvailable());
            product1.setDescription(product.getDescription());
            product1.setPrice(product.getPrice());
            product1.setName(product.getName());
            product1.setCategory(product.getCategory());
            //product1.setYear(product.getYear());
            return productRepository.save(product1);
        }
        catch (Exception e)
        {
          throw new ResourceNotFoundException("The product you are trying to update doesn't exist");
        }
    }

    public List<Product> searchProductsBasedOnDescription(String description)
    {
        List<Product> productList = productRepository.findProductsBasedOnDescription(description);
        if(productList.isEmpty())
        {
            throw new ResourceNotFoundException("The product you are searching of the description "+description+" are not present");
        }
        return productList;
    }

    public List<Product> searchProductsBasedOnCategory(String category)
    {
        List<Product> productList = productRepository.findProductsBasedOnCategory(category);
        if(productList.isEmpty())
        {
            throw new ResourceNotFoundException("There are no product for the category "+category);
        }
        return productList;
    }

    public List<Product> searchProductsBasedOnName(String name)
    {
        List<Product> productList = productRepository.findProductsBasedOnName(name);
        if(productList.isEmpty())
        {
            throw new ResourceNotFoundException("There are no product based on the name "+name);
        }
        return productList;
    }

    public List<Product> searchProductsBasedOnPriceRange(double price1, double price2)
    {
        List<Product> productList = productRepository.findProductsBasedOnPriceRange(price1,price2);
        if(productList.isEmpty())
        {
            throw new ResourceNotFoundException("There are no product in the price range you are searching for");
        }
        return productList;
    }
}
