package org.splitwise.app.service;

import org.splitwise.app.entity.Expense;
import org.splitwise.app.entity.User;
import org.splitwise.app.exception.ExpenseNotMatchingException;
import org.splitwise.app.repo.ExpenseRepository;
import org.splitwise.app.repo.GroupRepository;
import org.splitwise.app.repo.UserRepository;
import org.splitwise.app.util.TransformUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ExpenseService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final ExpenseRepository expenseRepository;

    public ExpenseService(UserRepository userRepository, GroupRepository groupRepository, ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.expenseRepository = expenseRepository;
    }

    public Expense addExpenseEqual(String payer, Double amount, List<String> participants){
        User user = userRepository.findByName(payer);
        Expense expense = new Expense(groupRepository.getGroup(), user, amount, "Equal Expense");
        participants.forEach(participant ->{
            User participantUser = userRepository.findByName(participant);
            if(participantUser != null && !participant.equals(payer)){
                Double paidAmount = TransformUtils.roundOff(amount/participants.size());
                expense.addPaidFor(participantUser, paidAmount);
            }
        });
        return expenseRepository.addExpense(expense);
    }

    public Expense addExpenseExact(String payer, Double amount, Map<String, Double> participants){
        User user = userRepository.findByName(payer);
        Expense expense = new Expense(groupRepository.getGroup(), user, amount, "Exact Expense");

        Double valuesSum = participants.values().stream().reduce(0.0, Double::sum);
        if(!Objects.equals(valuesSum, amount)){
            throw new ExpenseNotMatchingException("Amount not matching with participants sum");
        }
        participants.keySet().forEach(participant ->{
            User participantUser = userRepository.findByName(participant);
            if(participantUser != null && !participant.equals(payer)){
                Double paidAmount = TransformUtils.roundOff(participants.get(participant));
                expense.addPaidFor(participantUser, paidAmount);
            }
        });
        return expenseRepository.addExpense(expense);
    }

    public Expense addExpensePercent(String payer, Double amount, Map<String, Integer> participants){
        User user = userRepository.findByName(payer);
        Expense expense = new Expense(groupRepository.getGroup(), user, amount, "Percent Expense");

        Integer percentSum = participants.values().stream().reduce(0, Integer::sum);
        if(!Objects.equals(percentSum, 100)){
            throw new ExpenseNotMatchingException("Percentage not matching with participants percent sum");
        }
        participants.keySet().forEach(participant ->{
            User participantUser = userRepository.findByName(participant);
            if(participantUser != null && !participant.equals(payer)){
                Double paidAmount = TransformUtils.roundOff(participants.get(participant) * amount / 100.0);
                expense.addPaidFor(participantUser, paidAmount);
            }
        });
        return expenseRepository.addExpense(expense);
    }

}
