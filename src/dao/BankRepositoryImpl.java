package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import bean.*;
import bean.Transaction;
import util.DBUtil;

public class BankRepositoryImpl {

    private Connection conn;

    public BankRepositoryImpl() {
        conn = DBUtil.getConnection(); 
    }

    public int insertCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (first_name, last_name, DOB, email, phone_number, address) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, customer.getFirstName());
        ps.setString(2, customer.getLastName());
        ps.setString(3, customer.getDob());
        ps.setString(4, customer.getEmail());
        ps.setString(5, customer.getPhoneNumber());
        ps.setString(6, customer.getAddress());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1); 
        }
        return -1;
    }

    public int insertAccount(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (customer_id, account_type, balance) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, account.getCustomerId());
        ps.setString(2, account.getAccountType());
        ps.setDouble(3, account.getBalance());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1); 
        }
        return -1;
    }

    public Account getAccountById(int accountId) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, accountId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Account account = new Account();
            account.setAccountId(rs.getInt("account_id"));
            account.setCustomerId(rs.getInt("customer_id"));
            account.setAccountType(rs.getString("account_type"));
            account.setBalance(rs.getDouble("balance"));
            return account;
        }
        return null;
    }

    public void updateBalance(int accountId, double newBalance) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDouble(1, newBalance);
        ps.setInt(2, accountId);
        ps.executeUpdate();
    }

    public void insertTransaction(Transaction t) throws SQLException {
        String sql = "INSERT INTO transactions (account_id, transaction_type, amount, transaction_date) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, t.getAccountId());
        ps.setString(2, t.getTransactionType());
        ps.setDouble(3, t.getAmount());
        ps.setDate(4, t.getTransactionDate());
        ps.executeUpdate();
    }

    public List<Transaction> getTransactions(int accountId, String fromDate, String toDate) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_id = ? AND transaction_date BETWEEN ? AND ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, accountId);
        ps.setDate(2, Date.valueOf(fromDate));
        ps.setDate(3, Date.valueOf(toDate));

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Transaction t = new Transaction();
            t.setTransactionId(rs.getInt("transaction_id"));
            t.setAccountId(rs.getInt("account_id"));
            t.setTransactionType(rs.getString("transaction_type"));
            t.setAmount(rs.getDouble("amount"));
            t.setTransactionDate(rs.getDate("transaction_date"));
            transactions.add(t);
        }

        return transactions;
    }
}
