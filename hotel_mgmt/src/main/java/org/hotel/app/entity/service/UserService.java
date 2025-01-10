package org.hotel.app.entity.service;

import org.hotel.app.entity.User;
import org.hotel.app.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final List<User> users;

    public UserService(){
        users = new ArrayList<>();
    }

    public void addUser(String name){
        User user = new User(name);
        users.add(user);
    }

    public User findByName(String name){
        return users.stream()
                .filter(user -> user.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(name + " not found in userList"));
    }
}
