package com.cts.QsrManagement.service;

import com.cts.QsrManagement.dto.CartDTO;
import com.cts.QsrManagement.dto.UpdateCart;
import com.cts.QsrManagement.exception.ResourceNotFoundException;
import com.cts.QsrManagement.model.Product;
import com.cts.QsrManagement.model.Cart;
import com.cts.QsrManagement.model.User;
import com.cts.QsrManagement.repository.ProductRepository;
import com.cts.QsrManagement.repository.CartRepository;
import com.cts.QsrManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService
{
    @Autowired
    UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

   public List<Cart> getAllCarts()
   {
       List<Cart> cartList = cartRepository.findAll();
       if(cartList.isEmpty())
       {
           throw new ResourceNotFoundException("No items are selected");
       }
       return cartList;
   }



    public Cart createCart(CartDTO cartdto)
    {

        try
        {
        Product product = productRepository.findById(cartdto.getProductId()).get();
        User user = userRepository.findById(cartdto.getUserId()).get();
        Cart cartObj = new Cart(cartdto.getQuantity(), user, product);

        List<Cart> cartList = cartRepository.findAll();

        if (cartList != null) {
            for (Cart existingCart : cartList) {
                if (existingCart.getUser().getId() == cartObj.getUser().getId()) {
                    if (existingCart.getProduct().getId() == cartObj.getProduct().getId()) {
                        double price = existingCart.getPrice();

                        double productPrice = product.getPrice();
                        existingCart.setPrice((productPrice * cartObj.getQuantity()) + price);
                        existingCart.setQuantity(cartObj.getQuantity() + existingCart.getQuantity());
                        return cartRepository.save(existingCart);
                    }
                }
            }
        }

        double price = product.getPrice();
        cartObj.setPrice(price * cartObj.getQuantity());

        cartObj.setProduct(product);
        cartObj.setUser(userRepository.findById(cartObj.getUser().getId()).get());
        cartObj.setUser(user);
        Cart cart = cartRepository.save(cartObj);

        return cart;

    }catch (Exception e)
       {
           throw  new ResourceNotFoundException("The Selected product does not exist in the database");
       }
   }

   public void removeCart(Long id)
   {
       try
       {
           Cart cart = cartRepository.findById(id).get();
           cartRepository.deleteById(id);
       }
       catch (Exception e)
       {
           throw new ResourceNotFoundException("Something went wrong");
       }
   }

   public Cart updateCart(Long id, UpdateCart updateCart)
   {
        try {
            Cart cartObj = cartRepository.findById(id).get();
            Product product = productRepository.findById(updateCart.getProductId()).get();
            cartObj.setQuantity(updateCart.getQuantity());
            double productPrice = product.getPrice();
            cartObj.setProduct(product);
            cartObj.setPrice(productPrice * updateCart.getQuantity());
            return cartRepository.save(cartObj);
        }catch (Exception e){
           throw new ResourceNotFoundException("Check your input");
        }
   }

   public List<Cart> getCartsByUserId(Long userId)
   {
       return  cartRepository.findByUserId(userId);
   }

}
