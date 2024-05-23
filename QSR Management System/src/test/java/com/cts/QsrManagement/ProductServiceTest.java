package com.cts.QsrManagement;



import com.cts.QsrManagement.exception.ResourceNotFoundException;
import com.cts.QsrManagement.model.Product;
import com.cts.QsrManagement.repository.ProductRepository;
import com.cts.QsrManagement.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllBooks() {
        List<Product> productList = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(productList);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.getAllProducts();
        });
    }

    @Test
    public void testGetBookById() {
        Long bookId = 1L;
        Product product = new Product();
        product.setId(bookId);
        when(productRepository.findById(bookId)).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(bookId);

        Assertions.assertEquals(product, foundProduct);
    }



    @Test
    public void testUpdateBook_ExistingBook() {
        Long bookId = 1L;
        Product existingProduct = new Product();
        existingProduct.setId(bookId);
        existingProduct.setName("Existing Book");
        existingProduct.setDescription("Existing Author");

        when(productRepository.findById(bookId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        existingProduct.setName("Updated Title");
        existingProduct.setDescription("Updated Author");

        Product updatedProduct = productService.updateBook(existingProduct);

        Assertions.assertEquals(existingProduct.getId(), updatedProduct.getId());
        Assertions.assertEquals(existingProduct.getName(), updatedProduct.getName());
        Assertions.assertEquals(existingProduct.getDescription(), updatedProduct.getDescription());
    }

    @Test
    public void testDeleteBook_ExistingBook() {
        Long productId = 1L;
        Product existingProduct = new Product();
        existingProduct.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        productService.deleteBook(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }




}