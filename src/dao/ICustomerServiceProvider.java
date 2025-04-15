package dao;

import java.util.Date;
import java.util.List;
import bean.Account;
import bean.BankAccount;
import bean.Customer;
import bean.Transaction;

public interface ICustomerServiceProvider {
    double getAccountBalance(long accountNumber);
    double deposit(long accountNumber, float amount);
    double withdraw(long accountNumber, float amount);
    boolean transfer(long fromAccountNumber, long toAccountNumber, float amount);
    Account getAccountDetails(long accountNumber);
    List<Transaction> getTransactions(long accountNumber, Date fromDate, Date toDate);
	List<Transaction> getTransations(long accountNumber, Date fromDate, Date toDate);
	Customer createCustomer(String name, String email, String phone, String address);
	BankAccount createSavingsAccount(Customer customer, float initialBalance, float interestRate);
}