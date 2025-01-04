package org.splitwise.app.entity;

public class User {
    private final Integer userId;
    private String name;
    private String email;
    private static Integer idGenerator = 1;

    public User(String name, String email){
        this.name = name;
        this.email = email;
        this.userId = idGenerator++;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (!(obj instanceof User user)) return false;
        return getUserId()!= null && getUserId().equals(user.getUserId());
    }
}
