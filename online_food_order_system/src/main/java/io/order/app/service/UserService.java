package io.order.app.service;

import io.order.app.entity.User;
import io.order.app.exception.InvalidUserException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final List<User> users;
    private User userLogged;

    public UserService(){
        users = new ArrayList<>();
        users.add(new User("user1", "123"));
        users.add(new User("admin", "admin"));
    }

    public Boolean registerUser(String userName, String password){
        Optional<User> optionalUser = findByName(userName);
        if (optionalUser.isPresent())
            return Boolean.FALSE;
        User newUser = new User(userName, password);
        return users.add(newUser);
    }

    public User login(String userName, String password){
        if (userLogged != null) return null;
        userLogged = users.stream()
                .filter(user -> user.getName().equals(userName) && user.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new InvalidUserException("Invalid username password"));
        return userLogged;
    }

    public Boolean logout(){
        if(userLogged == null){
            return Boolean.FALSE;
        }
        userLogged = null;
        return Boolean.TRUE;
    }

    public User getCurrentlyLoggerUser(){
        return userLogged;
    }

    public Boolean isUserLoggedIn() { return userLogged!=null;}

    private Optional<User> findByName(String name){
        return users.stream()
                .filter(user -> user.getName().equals(name))
                .findFirst();
    }
}
