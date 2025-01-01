package org.library.app.repo;

import org.library.app.repo.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserRepository {
    private final List<User> users;

    public UserRepository(){
        this.users = new ArrayList<>();
    }

    public User save(User user){
        this.users.add(user);
        return this.users.get(this.users.size() - 1);
    }

    public User findByUserId(Integer userId){
        return users.stream()
                .filter(user -> Objects.equals(user.getUserId(), userId))
                .findFirst()
                .orElse(null);
    }
}
