package com.cts.QsrManagement.service;

import com.cts.QsrManagement.exception.ResourceNotFoundException;
import com.cts.QsrManagement.model.User;
import com.cts.QsrManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService
{

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers()
    {
        List<User> userList = userRepository.findAll();
        if(userList.isEmpty())
        {
            throw new ResourceNotFoundException("No user exists");
        }
        return userList;
    }

    public User getUserById(Long id)
    {
        Optional<User> user = userRepository.findById(id);
        try
        {
            return user.get();
        }
        catch (Exception e)
        {
            throw new ResourceNotFoundException("User with the id: "+id+" does not exist.");
        }
    }

    public User addUser(User userObj)
    {
        if(userObj.getId() ==null)
        {
            List<User> userList = getAllUsers();
            for(User u:userList)
            {
                if(u.getEmailId().equals(userObj.getEmailId()))
                {
                    throw new ResourceNotFoundException("User Already registered");
                }
            }
            userObj.setTotalOrder(0);
            return userRepository.save(userObj);
        }
        else
        {
            Long id = userObj.getId();
            getUserById(id);
            return userRepository.save(userObj);
        }
    }

    public void deleteUser(Long id)
    {
        try
        {
            userRepository.findById(id).get();
            userRepository.deleteById(id);
        }
        catch (Exception e)
        {
            throw new ResourceNotFoundException("User with id: "+id+" does not exist");
        }
    }

    public User updateUser(User user)
    {
        Long id = user.getId();
        getUserById(id);
        user.setTotalOrder(userRepository.findById(user.getId()).get().getTotalOrder());
        return userRepository.save(user);
    }

    public User logIn(String email,String password)
    {
        List<User> users = getAllUsers();

        for(User user:users)
        {
            if(user.getEmailId().equals(email) && user.getPassword().equals(password))
            {
                return user;
            }
        }
        throw new ResourceNotFoundException("Your password or email is incorrect");
    }
}
