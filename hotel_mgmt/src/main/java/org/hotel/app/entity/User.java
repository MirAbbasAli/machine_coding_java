package org.hotel.app.entity;

import java.util.Objects;

public class User {
    private final String name;

    public User(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if(this == o) return true;
        if (!(o instanceof User user)) return false;
        return name!=null && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
