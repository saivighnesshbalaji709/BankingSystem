package dao;

import bean.Customer;
import myexception.InsufficientFundException;
import myexception.InvalidAccountException;
import myexception.OverDraftLimitExcededException;
import bean.Account;
import bean.BankAccount;

import java.util.List;

public interface IBankServiceProvider {
    boolean createAccount(Customer customer, long accNo, String accType, float balance);
    List<Account> listAccounts();
    BankAccount getAccountDetails(long accountNumber) throws InvalidAccountException;
    void calculateInterest();
	Customer createCustomer(String name, String email, String phone, String address);
	BankAccount getAccountDetails(String accountNumber) throws InvalidAccountException;
	void deposit(BankAccount account, float amount);
	void withdraw(BankAccount account, float amount) throws InsufficientFundException, OverDraftLimitExcededException;
	void calculateInterest(BankAccount account);
}