package org.splitwise.app.service;

import org.splitwise.app.entity.Balance;
import org.splitwise.app.entity.Expense;
import org.splitwise.app.entity.User;
import org.splitwise.app.repo.BalanceRepository;

import java.util.List;

public class BalanceService {
    private final BalanceRepository balanceRepository;

    public BalanceService(BalanceRepository balanceRepository){
        this.balanceRepository = balanceRepository;
    }

    public void updateBalance(Expense expense){
        expense.getParticipants().forEach((participant, amount) -> {
            Balance balanceToGet = balanceRepository.findByUserAndOwedTo(expense.getPayer(), participant);
            if(balanceToGet == null){
                balanceToGet = new Balance(expense.getPayer(), amount, participant);
                balanceRepository.addBalance(balanceToGet);
            } else {
                balanceToGet.setAmount(balanceToGet.getAmount() + amount);
            }
            settleBalance(expense.getPayer(), participant);
        });
    }

    private void settleBalance(User userOne, User userTwo){
        Balance balanceBetweenOneAndTwo = balanceRepository.findByUserAndOwedTo(userOne, userTwo);
        Balance balanceBetweenTwoAndOne = balanceRepository.findByUserAndOwedTo(userTwo, userOne);

        if(balanceBetweenOneAndTwo != null && balanceBetweenTwoAndOne!= null){
            if(balanceBetweenOneAndTwo.getAmount() > balanceBetweenTwoAndOne.getAmount()){
                balanceBetweenOneAndTwo.setAmount(balanceBetweenOneAndTwo.getAmount() - balanceBetweenTwoAndOne.getAmount());
                balanceRepository.removeBalance(balanceBetweenTwoAndOne);
            } else if(balanceBetweenOneAndTwo.getAmount() < balanceBetweenTwoAndOne.getAmount()){
                balanceBetweenTwoAndOne.setAmount(balanceBetweenTwoAndOne.getAmount() - balanceBetweenOneAndTwo.getAmount());
                balanceRepository.removeBalance(balanceBetweenOneAndTwo);
            } else {
                balanceRepository.removeBalance(balanceBetweenOneAndTwo);
                balanceRepository.removeBalance(balanceBetweenTwoAndOne);
            }
        }
    }

    public void showBalances(){
        List<Balance> balances = balanceRepository.findAll();
        if(balances.isEmpty()){
            System.out.println("No balances");
            return;
        }
        balances.forEach(balance -> {
            System.out.printf("%s owes to %s %.2f%n", balance.getOwedTo().getName(), balance.getUser().getName(), balance.getAmount());
        });
    }

    public void showBalances(User user){
        List<Balance> balances = balanceRepository.findByUser(user);
        if(balances.isEmpty()){
            System.out.println("No balances");
            return;
        }
        balances.forEach(balance -> {
            System.out.printf("%s owes to %s %.2f%n", balance.getOwedTo().getName(), balance.getUser().getName(), balance.getAmount());
        });
    }
}
