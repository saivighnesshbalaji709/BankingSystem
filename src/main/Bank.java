package main;

import java.util.Scanner;

import bean.BankAccount;
import bean.Customer;
import dao.BankServiceImpl;
import myexception.InsufficientFundException;
import myexception.OverDraftLimitExcededException;

public class Bank {
    public static void main(String[] args) throws InsufficientFundException, OverDraftLimitExcededException {
        Scanner sc = new Scanner(System.in);
        BankServiceImpl service = new BankServiceImpl();

        System.out.println("Enter customer details:");
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        System.out.print("Address: ");
        String address = sc.nextLine();

        Customer customer = service.createCustomer(name, email, phone, address);
        BankAccount account = null;

        System.out.println("Choose Account Type:");
        System.out.println("1. Savings Account");
        System.out.println("2. Current Account");
        int choice = sc.nextInt();

        if (choice == 1) {
            account = service.createSavingsAccount(customer, 1000f, 4.5f);
        } else if (choice == 2) {
            account = service.createCurrentAccount(customer, 500f);
        } else {
            System.out.println("Invalid account type.");
            sc.close();
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("\n--- Banking Menu ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Calculate Interest");
            System.out.println("4. Show Account Info");
            System.out.println("5. Exit");
            System.out.print("Choose: ");
            int option = sc.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Enter amount to deposit: ");
                    float dep = sc.nextFloat();
                    service.deposit(account, dep);
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    float with = sc.nextFloat();
                    service.withdraw(account, with);
                    break;
                case 3:
                    service.calculateInterest(account);
                    break;
                case 4:
                    account.printDetails();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }

            System.out.println("Current Balance: " + account.getBalance());
        }

        sc.close();
        System.out.println("Thank you for using the banking system.");
    }
}
