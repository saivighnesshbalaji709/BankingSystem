package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import bean.Account;
import bean.BankAccount;
import bean.CurrentAccount;
import bean.Customer;
import bean.SavingsAccount;
import bean.Transaction;
import myexception.InsufficientFundException;
import myexception.InvalidAccountException;
import myexception.OverDraftLimitExcededException;

public class BankServiceImpl implements IBankServiceProvider, ICustomerServiceProvider {

    private final List<BankAccount> accountList = new ArrayList<>();
    private static final AtomicInteger accountCounter = new AtomicInteger(1000);
    private static final AtomicInteger customerCounter = new AtomicInteger(1);

    // Add new account to list
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
    }

    @Override
    public void withdraw(BankAccount account, float amount)
            throws InsufficientFundException, OverDraftLimitExcededException {

        if (account instanceof CurrentAccount && account.getBalance() + CurrentAccount.OVERDRAFT_LIMIT < amount) {
            throw new OverDraftLimitExcededException("Overdraft limit exceeded.");
        } else if (!(account instanceof CurrentAccount) && account.getBalance() < amount) {
            throw new InsufficientFundException("Insufficient funds.");
        }

        account.withdraw(amount);
    }

    @Override
    public void calculateInterest(BankAccount account) {
        account.calculateInterest();
    }

    @Override
    public List<BankAccount> getAllAccounts() {
        return accountList;
    }

    // Generate unique account number
    private String generateAccountNumber(String prefix) {
        return prefix + accountCounter.incrementAndGet();
    }

    // Generate unique customer ID
    private String generateCustomerId() {
        return "CUST" + customerCounter.incrementAndGet();
    }

    @Override
    public Customer createCustomer(String name, String email, String phone, String address) {
        return new Customer(generateCustomerId(), name, email, phone, address);
    }

    @Override
    public BankAccount createSavingsAccount(Customer customer, float initialBalance, float interestRate) {
        String accNo = generateAccountNumber("SA");
        BankAccount account = new SavingsAccount(accNo, customer, initialBalance, interestRate);
        accountList.add(account);
        return account;
    }

    @Override
    public BankAccount createCurrentAccount(Customer customer, float initialBalance) {
        String accNo = generateAccountNumber("CA");
        BankAccount account = new CurrentAccount(accNo, customer, initialBalance);
        accountList.add(account);
        return account;
    }

    @Override
    public float getAccountBalance(long accountNumber) throws InvalidAccountException {
        BankAccount account = getAccountByNumber(String.valueOf(accountNumber));
        if (account == null) {
            throw new InvalidAccountException("Account not found.");
        }
        return account.getBalance();
    }

    @Override
    public float deposit(long accountNumber, float amount) throws InvalidAccountException {
        BankAccount account = getAccountByNumber(String.valueOf(accountNumber));
        if (account == null) {
            throw new InvalidAccountException("Account not found.");
        }
        account.deposit(amount);
        return account.getBalance();
    }

    @Override
    public float withdraw(long accountNumber, float amount) throws InvalidAccountException, InsufficientFundException, OverDraftLimitExcededException {
        BankAccount account = getAccountByNumber(String.valueOf(accountNumber));
        if (account == null) {
            throw new InvalidAccountException("Account not found.");
        }
        withdraw(account, amount);
        return account.getBalance();
    }

    @Override
    public boolean transfer(long fromAccountNumber, long toAccountNumber, float amount) throws InvalidAccountException, InsufficientFundException, OverDraftLimitExcededException {
        BankAccount fromAccount = getAccountByNumber(String.valueOf(fromAccountNumber));
        BankAccount toAccount = getAccountByNumber(String.valueOf(toAccountNumber));

        if (fromAccount == null || toAccount == null) {
            throw new InvalidAccountException("One or both accounts not found.");
        }

        withdraw(fromAccount, amount);
        deposit(toAccount, amount);
        return true;
    }

   
    @Override
    public BankAccount getAccountDetails(long accountNumber) throws InvalidAccountException {
        return getAccountByNumber(String.valueOf(accountNumber));
    }

    @Override
    public BankAccount getAccountDetails(String accountNumber) throws InvalidAccountException {
        return getAccountByNumber(accountNumber);
    }

	public boolean createAccount(Customer customer, long accNo, String accType, float balance) {
		return false;
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Transaction> getTransactions(long accountNumber, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> listAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void calculateInterest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Transaction> getTransations(long accountNumber, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return null;
	}
}
