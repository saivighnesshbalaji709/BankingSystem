package bean;

public class SavingsAccount extends Account {
    private float interestRate;

    public SavingsAccount(float balance, Customer customer, float interestRate) {
        super();
        this.interestRate = interestRate;
    }

    public float getInterestRate() {
        return interestRate;
    }
}
