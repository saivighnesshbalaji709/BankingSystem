package app;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import bean.*;
import dao.BankRepositoryImpl;
import util.*;


public class BankApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankRepositoryImpl repo = new BankRepositoryImpl();

        while (true) {
            System.out.println("\nWelcome to the Bank App");
            System.out.println("1. Create Account");
            System.out.println("2. View Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transfer");
            System.out.println("6. View Transactions");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");
            int ch = scanner.nextInt();
            scanner.nextLine(); // consume newline

            try {
                switch (ch) {
                    case 1:
                        Customer customer = new Customer();
                        System.out.print("First Name: ");
                        customer.setFirstName(scanner.nextLine());
                        System.out.print("Last Name: ");
                        customer.setLastName(scanner.nextLine());
                        System.out.print("DOB (YYYY-MM-DD): ");
                        customer.setDob(scanner.nextLine());
                        System.out.print("Email: ");
                        customer.setEmail(scanner.nextLine());
                        System.out.print("Phone Number: ");
                        customer.setPhoneNumber(scanner.nextLine());
                        System.out.print("Address: ");
                        customer.setAddress(scanner.nextLine());

                        int custId = repo.insertCustomer(customer);
                        if (custId != -1) {
                            Account account = new Account();
                            account.setCustomerId(custId);
                            System.out.print("Account Type (savings/current/zero_balance): ");
                            account.setAccountType(scanner.nextLine());
                            account.setBalance(0.00);
                            int accId = repo.insertAccount(account);
                            System.out.println("Account created with ID: " + accId);
                        } else {
                            System.out.println("Customer creation failed.");
                        }
                        break;

                    case 2:
                        System.out.print("Enter Account ID: ");
                        int aid = scanner.nextInt();
                        Account acc = repo.getAccountById(aid);
                        if (acc != null) {
                            System.out.println("Account ID: " + acc.getAccountId());
                            System.out.println("Account Type: " + acc.getAccountType());
                            System.out.println("Balance: " + acc.getBalance());
                        } else {
                            System.out.println("Account not found.");
                        }
                        break;

                    case 3:
                        System.out.print("Enter Account ID: ");
                        int accId = scanner.nextInt();
                        System.out.print("Enter Amount to Deposit: ");
                        double depositAmt = scanner.nextDouble();
                        Account depAcc = repo.getAccountById(accId);
                        double newDepBal = depAcc.getBalance() + depositAmt;
                        repo.updateBalance(accId, newDepBal);

                        Transaction depTxn = new Transaction();
                        depTxn.setAccountId(accId);
                        depTxn.setTransactionType("deposit");
                        depTxn.setAmount(depositAmt);
                        depTxn.setTransactionDate(new java.sql.Date(System.currentTimeMillis()));
                        repo.insertTransaction(depTxn);
                        System.out.println("Deposit successful.");
                        break;

                    case 4:
                        System.out.print("Enter Account ID: ");
                        int withId = scanner.nextInt();
                        System.out.print("Enter Amount to Withdraw: ");
                        double withAmt = scanner.nextDouble();
                        Account withAcc = repo.getAccountById(withId);

                        if (withAcc.getBalance() >= withAmt) {
                            double newBal = withAcc.getBalance() - withAmt;
                            repo.updateBalance(withId, newBal);

                            Transaction withTxn = new Transaction();
                            withTxn.setAccountId(withId);
                            withTxn.setTransactionType("withdrawal");
                            withTxn.setAmount(withAmt);
                            withTxn.setTransactionDate(new java.sql.Date(System.currentTimeMillis()));
                            repo.insertTransaction(withTxn);

                            System.out.println("Withdrawal successful.");
                        } else {
                            System.out.println("Insufficient balance.");
                        }
                        break;

                    case 5:
                        System.out.print("From Account ID: ");
                        int fromId = scanner.nextInt();
                        System.out.print("To Account ID: ");
                        int toId = scanner.nextInt();
                        System.out.print("Amount: ");
                        double transferAmt = scanner.nextDouble();

                        Account fromAcc = repo.getAccountById(fromId);
                        Account toAcc = repo.getAccountById(toId);

                        if (fromAcc != null && toAcc != null && fromAcc.getBalance() >= transferAmt) {
                            repo.updateBalance(fromId, fromAcc.getBalance() - transferAmt);
                            repo.updateBalance(toId, toAcc.getBalance() + transferAmt);

                            Transaction t1 = new Transaction();
                            t1.setAccountId(fromId);
                            t1.setTransactionType("transfer");
                            t1.setAmount(transferAmt);
                            t1.setTransactionDate(new java.sql.Date(System.currentTimeMillis()));
                            repo.insertTransaction(t1);

                            Transaction t2 = new Transaction();
                            t2.setAccountId(toId);
                            t2.setTransactionType("deposit");
                            t2.setAmount(transferAmt);
                            t2.setTransactionDate(new java.sql.Date(System.currentTimeMillis()));
                            repo.insertTransaction(t2);

                            System.out.println("Transfer successful.");
                        } else {
                            System.out.println("Transfer failed. Check balance or account IDs.");
                        }
                        break;

                    case 6:
                        System.out.print("Enter Account ID: ");
                        int tid = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("From Date (YYYY-MM-DD): ");
                        String fdate = scanner.nextLine();
                        System.out.print("To Date (YYYY-MM-DD): ");
                        String tdate = scanner.nextLine();
                        List<Transaction> txns = repo.getTransactions(tid, fdate, tdate);
                        if (txns.isEmpty()) {
                            System.out.println("No transactions found.");
                        } else {
                            for (Transaction txn : txns) {
                                System.out.println(txn.getTransactionId() + " | " +
                                        txn.getTransactionType() + " | " +
                                        txn.getAmount() + " | " +
                                        txn.getTransactionDate());
                            }
                        }
                        break;

                    case 7:
                        System.out.println("Thank you for using Bank App!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Database error: " + e.getMessage());
            }
        }
    }
}
