# Secure Banking Application

## Project Title
Secure Banking Application

## Description
A console-based bank management application developed in Java demonstrating key Object-Oriented Programming (OOP) concepts alongside robust secure coding practices. Features key derivation function (PBKDF2) password hashing with unique salts, strict input sanitization, and flat-file data persistence.

## Student Details
- **Name:** [Your Full Name Here]
- **Registration Number:** [Your Reg Number Here]

## Features
- **Secure Authentication System:** User registration and login using PBKDF2 with SHA-256 password hashing and random cryptographic salt generation.
- **Account Management:** Capability to create unique account numbers linked to authenticated users and view current balance details.
- **Transactions Management:** Real-time deposit and withdrawal execution with strict validation against negative inputs and overdrafts.
- **Data Persistence:** Automated reading and writing of user accounts, transactions, and salt hashes into local text files (`users.txt`, `accounts.txt`, `transactions.txt`).

## How to Run the Application
1. Extract this zip file.
2. Open **IntelliJ IDEA**.
3. Go to **File -> Open...** and select the `SecureBankApp-REG123456` directory.
4. Locate `src/com/bank/Main.java`.
5. Right-click `Main.java` and select **Run 'Main.main()'**.
