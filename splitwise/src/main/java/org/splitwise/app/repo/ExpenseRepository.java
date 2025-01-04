package org.splitwise.app.repo;

import org.splitwise.app.entity.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository {
    private final List<Expense> expenses;

    public ExpenseRepository(){
        expenses = new ArrayList<>();
    }

    public Expense addExpense(Expense expense){
        this.expenses.add(expense);
        System.out.println("Expense added successfully");
        return expenses.get(expenses.size() - 1);
    }

    public List<Expense> findByGroupId(Integer groupId){
        return expenses.stream()
                .filter(expense -> expense.getGroup().getGroupId().equals(groupId))
                .toList();
    }
}
