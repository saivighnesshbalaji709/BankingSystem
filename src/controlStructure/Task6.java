package controlStructure;

import java.util.Scanner;

public class Task6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double[] transactions = new double[100];
        int count = 0;

        System.out.println("Bank transactions");

        while (true) {
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. View Transactions and Exit");
            System.out.print("enter an option ");
            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter deposit amount: ");
                double amount = sc.nextDouble();
                transactions[count] = amount;
                count++;
                System.out.println("Deposit successful.");
            } else if (choice == 2) {
                System.out.print("Enter withdrawal amount: ");
                double amount = sc.nextDouble();
                transactions[count] = -amount;
                count++;
                System.out.println("Withdrawal successful.");
            } else if (choice == 3) {
                System.out.println("\n Transaction History:");
                for (int i = 0; i < count; i++) {
                    if (transactions[i] > 0) {
                        System.out.println((i + 1) + ". Deposit: " + transactions[i]);
                    } else {
                        System.out.println((i + 1) + ". Withdrawal: " + Math.abs(transactions[i]));
                    }
                }
                System.out.println("Thank you");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        sc.close();
    }
}
