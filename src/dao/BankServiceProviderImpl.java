package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import bean.BankAccount;
import bean.CurrentAccount;
import bean.Customer;
import bean.SavingsAccount;
import bean.Transaction;
import myexception.InsufficientFundException;
import myexception.InvalidAccountException;
import myexception.OverDraftLimitExcededException;

public class BankServiceProviderImpl implements IBankServiceProvider, ICustomerServiceProvider {
    private final List<BankAccount> accountList = new ArrayList<>();
    private final List<String> transactionList = new ArrayList<>();
    private static final AtomicInteger accountCounter = new AtomicInteger(1000);
    private static final AtomicInteger customerCounter = new AtomicInteger(1);

    private String generateAccountNumber(String prefix) {
        return prefix + accountCounter.incrementAndGet();
    }

    private String generateCustomerId() {
        return "CUST" + customerCounter.incrementAndGet();
    }

    public void addAccount(BankAccount account) {
        accountList.add(account);
    }

    public BankAccount getAccountByNumber(String accNo) {
        for (BankAccount acc : accountList) {
            if (acc.getAccountNumber().equals(accNo)) return acc;
        }
        return null;
    }

    @Override
    public void deposit(BankAccount account, float amount) {
        account.deposit(amount);
        transactionList.add("Deposit of " + amount + " to account " + account.getAccountNumber());
    }

    @Override
    public void withdraw(BankAccount account, float amount) throws InsufficientFundException, OverDraftLimitExcededException {
        if (account instanceof CurrentAccount && account.getBalance() + CurrentAccount.OVERDRAFT_LIMIT < amount)
            throw new OverDraftLimitExcededException("Overdraft limit exceeded.");
        else if (!(account instanceof CurrentAccount) && account.getBalance() < amount)
            throw new InsufficientFundException("Insufficient funds.");
        account.withdraw(amount);
        transactionList.add("Withdrawal of " + amount + " from account " + account.getAccountNumber());
    }

    @Override
    public void calculateInterest(BankAccount account) {
        account.calculateInterest();
    }

    @Override
    public List<BankAccount> getAllAccounts() {
        return accountList;
    }

    @Override
    public Customer createCustomer(String name, String email, String phone, String address) {
        return new Customer(generateCustomerId(), name, email, phone, address);
    }

    @Override
    public BankAccount createSavingsAccount(Customer customer, float initialBalance, float interestRate) {
        BankAccount account = new SavingsAccount(generateAccountNumber("SA"), customer, initialBalance, interestRate);
        accountList.add(account);
        return account;
    }

    @Override
    public BankAccount createCurrentAccount(Customer customer, float initialBalance) {
        BankAccount account = new CurrentAccount(generateAccountNumber("CA"), customer, initialBalance);
        accountList.add(account);
        return account;
    }

    @Override
    public float getAccountBalance(long accountNumber) {
        for (BankAccount acc : accountList) {
            if (Long.parseLong(acc.getAccountNumber().replaceAll("\\D", "")) == accountNumber) {
                return acc.getBalance();
            }
        }
        return 0;
    }

    @Override
    public float deposit(long accountNumber, float amount) {
        for (BankAccount acc : accountList) {
            if (Long.parseLong(acc.getAccountNumber().replaceAll("\\D", "")) == accountNumber) {
                acc.deposit(amount);
                transactionList.add("Deposit of " + amount + " to account " + acc.getAccountNumber());
                return acc.getBalance();
            }
        }
        return 0;
    }

    @Override
    public float withdraw(long accountNumber, float amount) {
        for (BankAccount acc : accountList) {
            if (Long.parseLong(acc.getAccountNumber().replaceAll("\\D", "")) == accountNumber) {
                try {
                    withdraw(acc, amount);
                } catch (Exception e) {
                    return -1;
                }
                return acc.getBalance();
            }
        }
        return 0;
    }

    @Override
    public boolean transfer(long fromAccountNumber, long toAccountNumber, float amount) {
        BankAccount from = null, to = null;
        for (BankAccount acc : accountList) {
            long accNum = Long.parseLong(acc.getAccountNumber().replaceAll("\\D", ""));
            if (accNum == fromAccountNumber) from = acc;
            if (accNum == toAccountNumber) to = acc;
        }
        if (from == null || to == null) return false;
        try {
            withdraw(from, amount);
            deposit(to, amount);
            transactionList.add("Transfer of " + amount + " from " + from.getAccountNumber() + " to " + to.getAccountNumber());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<> getTransactions(long accountNumber, Date fromDate, Date toDate) {
        return transactionList;
    }
  
    @Override
    public BankAccount getAccountDetails(long accountNumber) throws InvalidAccountException {
        for (BankAccount acc : accountList) {
            if (Long.parseLong(acc.getAccountNumber().replaceAll("\\D", "")) == accountNumber) {
                return acc;
            }
        }
        throw new InvalidAccountException("Account not found.");
    }

	@Override
	public BankAccount getAccountDetails(String accountNumber) throws InvalidAccountException {
		// TODO Auto-generated method stub
		return null;
	}
}
