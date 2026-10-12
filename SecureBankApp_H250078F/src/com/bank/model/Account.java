package com.bank.model;

public class Account {
    private final String accountNumber;
    private final String username;
    private double balance;

    public Account(String accountNumber, String username, double balance) {
        this.accountNumber = accountNumber;
        this.username = username;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getUsername() { return username; }
    public double getBalance() { return balance; }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    public String toFileString() {
        return accountNumber + "," + username + "," + String.format("%.2f", balance);
    }

    public static Account fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length < 3) return null;
        return new Account(parts[0], parts[1], Double.parseDouble(parts[2]));
    }
}
