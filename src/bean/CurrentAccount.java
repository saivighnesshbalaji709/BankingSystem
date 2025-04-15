package bean;

public class CurrentAccount extends Account {
    public static final float OVERDRAFT_LIMIT = 0;
	private float overdraftLimit;

    public CurrentAccount(float balance, Customer customer, float overdraftLimit) {
        super();
        this.overdraftLimit = overdraftLimit;
    }

    public float getOverdraftLimit() {
        return overdraftLimit;
    }
}
