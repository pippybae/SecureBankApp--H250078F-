package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.model.User;

import java.io.*;
import java.util.*;

public class FileRepository {
    private static final String USERS_FILE = "users.txt";
    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";

    public FileRepository() {
        ensureFileExists(USERS_FILE);
        ensureFileExists(ACCOUNTS_FILE);
        ensureFileExists(TRANSACTIONS_FILE);
    }

    private void ensureFileExists(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error initializing file: " + fileName);
        }
    }

    public Map<String, User> loadUsers() {
        Map<String, User> users = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromFileString(line);
                if (user != null) users.put(user.getUsername(), user);
            }
        } catch (IOException e) {
            System.err.println("Failed to load users.");
        }
        return users;
    }

    public void saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(user.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving user.");
        }
    }

    public Map<String, Account> loadAccounts() {
        Map<String, Account> accounts = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Account account = Account.fromFileString(line);
                if (account != null) accounts.put(account.getAccountNumber(), account);
            }
        } catch (IOException e) {
            System.err.println("Failed to load accounts.");
        }
        return accounts;
    }

    public void saveAllAccounts(Collection<Account> accounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE, false))) {
            for (Account account : accounts) {
                writer.write(account.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating accounts file.");
        }
    }

    public void logTransaction(Transaction tx) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE, true))) {
            writer.write(tx.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error logging transaction.");
        }
    }
}
