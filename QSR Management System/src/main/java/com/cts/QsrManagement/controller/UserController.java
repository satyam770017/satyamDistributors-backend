package com.cts.QsrManagement.controller;


import com.cts.QsrManagement.model.User;
import com.cts.QsrManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping("/users")
public class UserController
{

    @Autowired
    private UserService userService;

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers()
    {
        List<User> loginList = userService.getAllUsers();
        return new ResponseEntity<>(loginList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id)
    {
       User user = userService.getUserById(id);
       return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User userObj)
    {
        User user = userService.addUser(userObj);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id)
    {
        userService.deleteUser(id);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User userObj)
    {
        User user = userService.updateUser(userObj);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/{email}/{password}")
    public User logIn(@PathVariable String email,@PathVariable String password)
    {
        User user = userService.logIn(email,password);
        return user;
    }


}
