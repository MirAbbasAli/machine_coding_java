package org.splitwise.app.entity;

public class Balance {
    private User user;
    private Double amount;
    private User owedTo;

    public Balance(User user, Double amount, User owedTo) {
        this.user = user;
        this.amount = amount;
        this.owedTo = owedTo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getOwedTo() {
        return owedTo;
    }

    public void setOwedTo(User owedTo) {
        this.owedTo = owedTo;
    }
}
