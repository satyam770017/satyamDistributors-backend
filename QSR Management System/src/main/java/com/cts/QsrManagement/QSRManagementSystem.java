package com.cts.QsrManagement;

import com.cts.QsrManagement.model.User;
import com.cts.QsrManagement.repository.ProductRepository;
import com.cts.QsrManagement.repository.OrderItemsRepository;
import com.cts.QsrManagement.service.ProductService;
import com.cts.QsrManagement.service.CartService;
import com.cts.QsrManagement.service.OrderService;
import com.cts.QsrManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QSRManagementSystem implements CommandLineRunner
{
	@Autowired
	CartService cartService;

	@Autowired
	ProductService productService;

	@Autowired
	UserService userService;

	@Autowired
	OrderService orderService;

	@Autowired
	OrderItemsRepository orderItemsRepository;

	@Autowired
	ProductRepository productRepository;

	public static void main(String[] args)  {
		SpringApplication.run(QSRManagementSystem.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{
//		User user = new User("Akash", "8908764561", "Akash@gmail.com", "Akash@1234", "Kolkata","Contai", "West Bengal", 721401, "Home", "User");
//		System.out.println(user);
		//userService.addUser(new User("Akash", "8908764561", "Akash@gmail.com", "Akash@1234", "Kolkata","Contai", "West Bengal", 721401, "Home", "User"));
		//Cart cart = new Cart()
		//Book book = bookService.getBookById(2L);
		//User user = userService.getUserById(3L);
//		CartDTO cartDTO = new CartDTO(2,2L,3L);
//		//cart.setQuantity(2);
//		//cart.setBook(bookService.getBookById(3L));
//		//cart.setUser(userService.getUserById(3L));
////		cartService.createCart(cartDTO);
//		Book book = bookService.getBookById(2L);
//		book.setPrice(100.50);
//		bookService.updateBook(book);
//		Order order = orderService.getOrderById(2L);
//		Set<OrderItems> orderList = order.getOrderItems();
//		for (OrderItems e: orderList){
//			System.out.println(e);
//		}

	}
}
