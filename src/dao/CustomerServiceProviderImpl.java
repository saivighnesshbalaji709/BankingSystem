package dao;

import dao.ICustomerServiceProvider;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import bean.Account;
import bean.Customer;
import bean.Transaction;

public class CustomerServiceProviderImpl implements ICustomerServiceProvider {
    private static final AtomicInteger accountCounter = new AtomicInteger(1000);
    private static final AtomicInteger customerCounter = new AtomicInteger(1);

    private String generateAccountNumber(String prefix) {
        return prefix + accountCounter.incrementAndGet();
    }

    private String generateCustomerId() {
        return "CUST" + customerCounter.incrementAndGet();
    }

    @Override
    public Customer createCustomer(String name, String email, String phone, String address) {
        String id = generateCustomerId();
        return new Customer();
    }

    @Override
    public BankAccount createSavingsAccount(Customer customer, float initialBalance, float interestRate) {
        String accNo = generateAccountNumber("SA");
        return new SavingsAccount(accNo, customer, initialBalance, interestRate);
    }

    @Override
    public BankAccount createCurrentAccount(Customer customer, float initialBalance) {
        String accNo = generateAccountNumber("CA");
        return new CurrentAccount(accNo, customer, initialBalance);
    }

	@Override
	public double getAccountBalance(long accountNumber) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double deposit(long accountNumber, float amount) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double withdraw(long accountNumber, float amount) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean transfer(long fromAccountNumber, long toAccountNumber, float amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Account getAccountDetails(long accountNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transaction> getTransactions(long accountNumber, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transaction> getTransations(long accountNumber, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return null;
	}
}
