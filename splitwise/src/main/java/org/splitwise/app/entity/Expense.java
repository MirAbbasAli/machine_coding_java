package org.splitwise.app.entity;

import java.util.HashMap;
import java.util.Map;

public class Expense {
    private final Integer expenseId;
    private Group group;
    private User payer;
    private String description;
    private Double amount;
    private Map<User, Double> participants;
    private static Integer idGenerator = 1;

    public Expense(Group group, User payer, Double amount, String description){
        this.group = group;
        this.payer = payer;
        this.amount = amount;
        this.description = description;
        this.participants = new HashMap<>();
        this.expenseId = idGenerator++;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getExpenseId() {
        return expenseId;
    }

    public Group getGroup() {
        return group;
    }

    public User getPayer() {
        return payer;
    }

    public Double getAmount() {
        return amount;
    }

    public Map<User, Double> getParticipants() {
        return participants;
    }

    public void addPaidFor(User user, Double amount){
        participants.put(user, amount);
    }
}
