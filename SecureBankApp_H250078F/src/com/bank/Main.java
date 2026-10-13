package com.bank;

import com.bank.model.Account;
import com.bank.service.BankingService;

import java.util.Scanner;

public class Main {
    private static final BankingService bankService = new BankingService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("   WELCOME TO SECURE BANKING APPLICATION  ");
        System.out.println("==========================================");

        while (true) {
            if (bankService.getCurrentUser() == null) {
                showAuthMenu();
            } else {
                showMainMenu();
            }
        }
    }
    private static void showAuthMenu() {
        System.out.println("\n1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Select an option: ");

        int choice = readIntChoice();
        switch (choice) {
            case 1 -> handleLogin();
            case 2 -> handleRegister();
            case 3 -> {
                System.out.println("Thank you for using Secure Banking App. Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice! Please select 1, 2, or 3.");
        }
    }

    private static void showMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Create Account");
        System.out.println("2. View Account Balance");
        System.out.println("3. Deposit Funds");
        System.out.println("4. Withdraw Funds");
        System.out.println("5. Logout");
        System.out.print("Select an option: ");

        int choice = readIntChoice();
        switch (choice) {
            case 1 -> handleCreateAccount();
            case 2 -> handleViewBalance();
            case 3 -> handleDeposit();
            case 4 -> handleWithdraw();
            case 5 -> bankService.logout();
            default -> System.out.println("Invalid choice!");
        }
    }

    private static void handleRegister() {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter Password (min 6 characters): ");
        String password = scanner.nextLine();

        if (bankService.registerUser(username, password)) {
            System.out.println("Registration successful! You can now log in.");
        } else {
            System.out.println("Registration failed! User may already exist or password was too short.");
        }
    }

    private static void handleLogin() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (bankService.login(username, password)) {
            System.out.println("Login successful! Welcome, " + username + ".");
        } else {
            System.out.println("Invalid credentials. Try again.");
        }
    }

    private static void handleCreateAccount() {
        if (bankService.getUserAccount() != null) {
            System.out.println("You already have an active bank account.");
            return;
        }

        System.out.print("Enter Initial Deposit Amount: ");
        double initialDeposit = readDoubleChoice();
        Account acc = bankService.createAccount(initialDeposit);
        if (acc != null) {
            System.out.println("Account created successfully! Account Number: " + acc.getAccountNumber());
        } else {
            System.out.println("Failed to create account. Initial deposit cannot be negative.");
        }
    }

    private static void handleViewBalance() {
        Account acc = bankService.getUserAccount();
        if (acc != null) {
            System.out.printf("Account Number: %s | Current Balance: $%.2f%n", acc.getAccountNumber(), acc.getBalance());
        } else {
            System.out.println("No account found. Please create an account first.");
        }
    }

    private static void handleDeposit() {
        Account acc = bankService.getUserAccount();
        if (acc == null) {
            System.out.println("No account found. Create an account first.");
            return;
        }

        System.out.print("Enter Deposit Amount: ");
        double amount = readDoubleChoice();
        if (bankService.deposit(amount)) {
            System.out.printf("Deposit successful! New Balance: $%.2f%n", acc.getBalance());
        } else {
            System.out.println("Deposit failed! Amount must be greater than zero.");
        }
    }

    private static void handleWithdraw() {
        Account acc = bankService.getUserAccount();
        if (acc == null) {
            System.out.println("No account found. Create an account first.");
            return;
        }

        System.out.print("Enter Withdrawal Amount: ");
        double amount = readDoubleChoice();
        if (bankService.withdraw(amount)) {
            System.out.printf("Withdrawal successful! Remaining Balance: $%.2f%n", acc.getBalance());
        } else {
            System.out.println("Withdrawal failed! Check balance or amount.");
        }
    }

    private static int readIntChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static double readDoubleChoice() {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1.0;
        }
    }
}
