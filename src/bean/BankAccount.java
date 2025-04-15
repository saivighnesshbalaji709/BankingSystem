package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import myexception.InsufficientFundException;
import myexception.OverDraftLimitExcededException;

public abstract class BankAccount {
    protected String accountNumber;
    protected Customer customer; // Has-A Relationship
    protected float balance;

    public BankAccount(String accountNumber, Customer customer, float balance) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public Customer getCustomer() { return customer; }
    public float getBalance() { return balance; }

    public void printDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: " + balance);
        customer.printDetails();
    }

    public abstract void deposit(float amount);
    public abstract void withdraw(float amount) throws InsufficientFundException, OverDraftLimitExcededException;
    public abstract void calculateInterest();

    public List<Transaction> getTransactions(Date fromDate, Date toDate, Transaction[] transactions) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (!((Date) transaction.getDate()).before(fromDate) && !((Date) transaction.getDate()).after(toDate)) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }
    private List<Transaction> transactions = new ArrayList<>();

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<Transaction> getTransactions(Date fromDate, Date toDate) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction tx : transactions) {
            if ((tx.getDate().equals(fromDate) || ((Date) tx.getDate()).after(fromDate)) &&
                (tx.getDate().equals(toDate) || ((Date) tx.getDate()).before(toDate))) {
                result.add(tx);
            }
        }
        return result;
    }
}
