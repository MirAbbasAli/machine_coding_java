package org.splitwise.app.repo;

import org.splitwise.app.entity.Balance;
import org.splitwise.app.entity.User;

import java.util.ArrayList;
import java.util.List;

public class BalanceRepository {
    private final List<Balance> balances;

    public BalanceRepository(){
        balances = new ArrayList<>();
    }

    public void addBalance(Balance balance){
        this.balances.add(balance);
    }

    public List<Balance> findByUser(User user){
        return balances.stream()
                .filter(balance -> balance.getUser().equals(user))
                .toList();
    }

    public List<Balance> findByOwedTo(User owedTo){
        return balances.stream()
                .filter(balance -> balance.getOwedTo().equals(owedTo))
                .toList();
    }

    public Balance findByUserAndOwedTo(User user, User owedTo){
        return balances.stream()
                .filter(balance -> balance.getUser().equals(user) && balance.getOwedTo().equals(owedTo))
                .findFirst()
                .orElse(null);
    }

    public List<Balance> findAll(){
        return balances;
    }

    public Boolean removeBalance(Balance balance){
        return balances.remove(balance);
    }
}
