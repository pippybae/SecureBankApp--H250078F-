package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.model.User;
import com.bank.repository.FileRepository;
import com.bank.security.PasswordUtil;

import java.util.*;

public class BankingService {
    private final FileRepository repository;
    private final Map<String, User> users;
    private final Map<String, Account> accounts;
    private User currentUser;

    public BankingService() {
        this.repository = new FileRepository();
        this.users = repository.loadUsers();
        this.accounts = repository.loadAccounts();
    }

    public boolean registerUser(String username, String password) {
        if (users.containsKey(username) || username.isBlank() || password.length() < 6) {
            return false;
        }
        String salt = PasswordUtil.generateSalt();
        String hash = PasswordUtil.hashPassword(password, salt);
        User user = new User(username, hash, salt);
        
        users.put(username, user);
        repository.saveUser(user);
        return true;
    }

    public boolean login(String username, String password) {
        User user = users.get(username);
        if (user != null && PasswordUtil.verifyPassword(password, user.getPasswordHash(), user.getSalt())) {
            this.currentUser = user;
            return true;
        }
        return false;
    }

    public void logout() {
        this.currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Account createAccount(double initialDeposit) {
        if (currentUser == null || initialDeposit < 0) return null;
        
        String accNum = "ACC" + (100000 + new Random().nextInt(900000));
        Account account = new Account(accNum, currentUser.getUsername(), initialDeposit);
        
        accounts.put(accNum, account);
        repository.saveAllAccounts(accounts.values());
        
        if (initialDeposit > 0) {
            repository.logTransaction(new Transaction(accNum, "INITIAL_DEPOSIT", initialDeposit));
        }
        return account;
    }

    public Account getUserAccount() {
        if (currentUser == null) return null;
        for (Account acc : accounts.values()) {
            if (acc.getUsername().equals(currentUser.getUsername())) {
                return acc;
            }
        }
        return null;
    }

    public boolean deposit(double amount) {
        Account acc = getUserAccount();
        if (acc != null && amount > 0) {
            acc.deposit(amount);
            repository.saveAllAccounts(accounts.values());
            repository.logTransaction(new Transaction(acc.getAccountNumber(), "DEPOSIT", amount));
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        Account acc = getUserAccount();
        if (acc != null && acc.withdraw(amount)) {
            repository.saveAllAccounts(accounts.values());
            repository.logTransaction(new Transaction(acc.getAccountNumber(), "WITHDRAWAL", amount));
            return true;
        }
        return false;
    }
}
