package controlStructure;
import java.util.*;

public class Task2 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
        double balance;
        System.out.print("Enter your current balance: ");
        balance = sc.nextDouble();

        while (true) {
            System.out.println("Welcome to ATM");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Your current balance is:" + balance);
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw:");
                    double withdrawAmount = sc.nextDouble();

                    if (withdrawAmount % 100 != 0 && withdrawAmount % 500 != 0) {
                        System.out.println("Withdrawal amount must be in multiples of 100 or 500.");
                    } else if (withdrawAmount > balance) {
                        System.out.println("Insufficient balance!");
                    } else if (withdrawAmount <= 0) {
                        System.out.println("Please enter a valid amount!");
                    } else {
                        balance -= withdrawAmount;
                        System.out.println("Withdrawal successful! New balance is" + balance);
                    }
                    break;

                case 3:
                    System.out.print("Enter amount to deposit:");
                    double depositAmount = sc.nextDouble();

                    if (depositAmount <= 0) {
                        System.out.println("Please enter a valid amount.");
                    } else {
                        balance += depositAmount;
                        System.out.println("Deposited successfully! New balance is:" + balance);
                    }
                    break;

                case 4:
                    System.out.println("Thank you");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid option.Please try again.");
            }
        }
    }
}

