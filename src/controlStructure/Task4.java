package controlStructure;
import java.util.*;

public class Task4 {
    public static void main(String[] args) {
        int[] accountNumber = {1001, 1002, 1003, 1004, 1005};
        double[] balance = {5000.00, 12000.50, 3050.75, 100.00, 780.20};
        Scanner s = new Scanner(System.in);
        boolean valid = false;
        while (!valid) {
            System.out.println("Enter your account number:");
            int enteredAccount = s.nextInt();
            for (int i = 0; i < accountNumber.length; i++) {
                if (enteredAccount == accountNumber[i]) {
                    System.out.println("Your balance is:" + balance[i]);
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                System.out.println("Invalid account number. Please try again.");
            }
        }
    }
}
