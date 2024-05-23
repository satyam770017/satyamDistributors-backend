package com.cts.QsrManagement.service;


import com.cts.QsrManagement.exception.ResourceNotFoundException;
import com.cts.QsrManagement.model.*;
import com.cts.QsrManagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService
{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    OrderItemsRepository orderItemsRepository;

    @Autowired
    UserRepository userRepository;

    public List<Order> getAllOrders()
    {
        List<Order> orderList = orderRepository.findAll();
        if(orderList.isEmpty())
        {
            throw new ResourceNotFoundException("No orders are placed yet");
        }
        return orderList;
    }

    public Order getOrderById(Long id)
    {
        try
        {
            Order order = orderRepository.findById(id).get();
            return order;
        }
        catch (Exception e)
        {
            throw new ResourceNotFoundException("Wrong order id : "+id);
        }
    }



    public Order placeOrder(Long userId)
    {
        User user = userRepository.findById(userId).get();
        List<Order> orderList = orderRepository.findAll();

        Order order = new Order();

        List<Cart> cartList = cartRepository.findByUserId(user.getId());
        int quantity = 0;
        double price = 0;

        for(Cart existingCart:cartList)
        {
            quantity+=existingCart.getQuantity();
            price = existingCart.getPrice()+price;
        }


        order.setTotalQuantity(quantity);
        order.setTotalPrice(totalPriceAfterDiscount(quantity,price));


        order.setUser(userRepository.findById(userId).get());
        order.setOrderDate(LocalDate.now());
        order.setStatus("Success");
        Order order1 = orderRepository.save(order);

        for(Cart existingCart:cartList)
        {
            OrderItems orderItem = new OrderItems(existingCart.getQuantity(),existingCart.getPrice(),order1,existingCart.getProduct());

            Product product = productRepository.findById(existingCart.getProduct().getId()).get();
            product.setStockAvailable(product.getStockAvailable()-existingCart.getQuantity());
            productRepository.save(product);
            orderItemsRepository.save(orderItem);
        }

        cartRepository.deleteByUserId(userId);
        return order1;
    }

    public String removeOrder(Long id)
    {
        Order order = getOrderById(id);
        LocalDate orderedDate = order.getOrderDate();
        LocalDate currentDate = LocalDate.now();
        if(orderedDate.plusDays(1).compareTo(currentDate)<0)
        {
            return "Your order cannot be cancelled";
        }
        else
        {
            order.setStatus("Cancelled");
            List<OrderItems> orderItemsList = orderItemsRepository.findAll();
            for(OrderItems e : orderItemsList){
                if(e.getOrder().getId()==id){
                    Product product = productRepository.findById(e.getProduct().getId()).get();
                    product.setStockAvailable(e.getQuantity()+ product.getStockAvailable());
                }
            }
            orderRepository.save(order);
            return "Your order has been cancelled successfully";
        }
    }

    public double totalPriceAfterDiscount(int quantity,double price)
    {


        if(quantity>=50)
        {
             price = price*(1-0.30);
        }
        else if (quantity>=25)
        {
            price = price*(1-0.20);
        }
        else if(quantity>=10)
        {
            price =price*(1-0.10);
        }
        else
        {
            price = price;
        }
       return price;
    }

    public List<Order> getOrdersByUserId(Long userId)
    {
        return  orderRepository.findByUserId(userId);
    }


}
