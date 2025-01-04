package org.splitwise.app.repo;

import org.splitwise.app.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserRepository {
    private final List<User> users;

    public UserRepository() {
        users = new ArrayList<>();
    }

    public void addUser(User user){
        this.users.add(user);
    }

    public List<User> findAll(){
        return users;
    }


    public User findByUserId(Integer userId){
        return users.stream()
                .filter(user -> Objects.equals(user.getUserId(), userId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User with " + userId + " not found"));
    }

    public User findByName(String name){
        return users.stream()
                .filter(user -> user.getName().equals(name))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("User with name " + name + "not found"));
    }
}
