package com.bank.model;

import java.time.LocalDateTime;

public class Transaction {
    private String accountNumber;
    private String type;
    private double amount;
    private String timestamp;

    public Transaction(String accountNumber, String type, double amount, String timestamp) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Transaction(String accountNumber, String type, double amount) {
        this(accountNumber, type, amount, LocalDateTime.now().toString());
    }

    public String toFileString() {
        return accountNumber + "," + type + "," + amount + "," + timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: $%.2f", timestamp, type, amount);
    }
}
