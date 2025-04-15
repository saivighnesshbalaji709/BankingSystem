package dao;

import java.util.Date;
import java.util.List;

import bean.Account;
import bean.Customer;
import bean.Transaction;

public interface IBankRepository {

    boolean createAccount(Customer customer, long accNo, String accType, float balance);

    List<Account> listAccounts();

    void calculateInterest(); 

    float getAccountBalance(long accountNumber);

    float deposit(long accountNumber, float amount);

    float withdraw(long accountNumber, float amount);

    boolean transfer(long fromAccount, long toAccount, float amount);

    Account getAccountDetails(long accountNumber);

    List<Transaction> getTransactions(long accountNumber, Date fromDate, Date toDate);

	List<Transaction> getTransactions(long accountNumber, java.sql.Date fromDate, java.sql.Date toDate);
}
