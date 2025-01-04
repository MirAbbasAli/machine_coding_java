package org.splitwise.app.util;

import org.splitwise.app.entity.Expense;
import org.splitwise.app.entity.User;
import org.splitwise.app.repo.BalanceRepository;
import org.splitwise.app.repo.ExpenseRepository;
import org.splitwise.app.repo.GroupRepository;
import org.splitwise.app.repo.UserRepository;
import org.splitwise.app.service.BalanceService;
import org.splitwise.app.service.ExpenseService;
import org.splitwise.app.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InputDriver {
    private static final Scanner in = new Scanner(System.in);
    private final UserRepository userRepository;
    private final BalanceService balanceService;
    private final UserService userService;
    private final ExpenseService expenseService;

    public InputDriver() {
        this.userRepository = new UserRepository();
        ExpenseRepository expenseRepository = new ExpenseRepository();
        GroupRepository groupRepository = new GroupRepository();
        BalanceRepository balanceRepository = new BalanceRepository();

        this.balanceService = new BalanceService(balanceRepository);
        this.userService = new UserService(userRepository, groupRepository);
        this.expenseService = new ExpenseService(userRepository, groupRepository, expenseRepository);
    }

    private void createUser(String name, String email){
        userService.addUser(name, email);
    }

    public void createGroup(String groupName, List<String> members){
        userService.createGroup(groupName);
        userService.addGroupMember(members);
    }

    private void createExpenseEqual(String payerName, Double amount, List<String> participants){
        Expense expense = expenseService.addExpenseEqual(payerName, amount, participants);
        balanceService.updateBalance(expense);
    }

    private void createExpenseExact(String payerName, Double amount, List<String> participants, List<Double> amounts){
        Map<String, Double> participantMap = IntStream.range(0, participants.size())
                .boxed()
                .collect(Collectors.toMap(participants::get, amounts::get));
        Expense expense = expenseService.addExpenseExact(payerName, amount, participantMap);
        balanceService.updateBalance(expense);
    }

    private void createExpensePercentage(String payerName, Double amount, List<String> participants, List<Integer> percentages){
        Map<String, Integer> participantMap = IntStream.range(0, participants.size())
                .boxed()
                .collect(Collectors.toMap(participants::get, percentages::get));
        Expense expense = expenseService.addExpensePercent(payerName, amount, participantMap);
        balanceService.updateBalance(expense);
    }

    private void showBalance(String userName){
        if(userName == null){
            balanceService.showBalances();
        }
        else{
            User user = userRepository.findByName(userName);
            balanceService.showBalances(user);
        }

    }

    public void runApp(){
        String invalidMessage = "Invalid Input, Try again";
        boolean running = true;
        while(running){
            try {
                String userInput = in.nextLine();
                List<String> commands = Arrays.stream(userInput.split(" ")).toList();
                switch (commands.get(0).toUpperCase()) {
                    case "EXIT":
                        running = false;
                        break;
                    case "CREATE_USER":
                        createUser(commands.get(1), commands.get(2));
                        break;
                    case "CREATE_GROUP":
                        String groupName = commands.get(1);
                        List<String> users = Arrays.stream(commands.get(2).split(",")).toList();
                        createGroup(groupName, users);
                        break;
                    case "EXPENSE":
                        String payerName = commands.get(1);
                        Double amount = Double.parseDouble(commands.get(2));
                        Integer noOfParticipants = Integer.parseInt(commands.get(3));
                        List<String> participants = commands.subList(4, 4 + noOfParticipants);
                        String paymentType = commands.get(4 + noOfParticipants).toUpperCase();
                        switch (paymentType) {
                            case "EQUAL":
                                createExpenseEqual(payerName, amount, participants);
                                break;
                            case "EXACT":
                                List<Double> amounts = commands.subList(5 + noOfParticipants, commands.size())
                                        .stream()
                                        .map(Double::parseDouble)
                                        .toList();
                                createExpenseExact(payerName, amount, participants, amounts);
                                break;
                            case "PERCENT":
                                List<Integer> percentages = commands.subList(5 + noOfParticipants, commands.size())
                                        .stream()
                                        .map(Integer::parseInt)
                                        .toList();
                                createExpensePercentage(payerName, amount, participants, percentages);
                                break;
                            default:
                                System.out.println(invalidMessage);
                        }
                        break;
                    case "SHOW":
                        if (commands.size() == 1)
                            showBalance(null);
                        else
                            showBalance(commands.get(1));
                        break;
                    default:
                        System.out.println(invalidMessage);
                }
            } catch (Exception e){
                System.out.println(invalidMessage);
            }
        }
    }
}
