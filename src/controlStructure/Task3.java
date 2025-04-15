package controlStructure;
import java.util.*;

public class Task3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of customers:");
        int n = sc.nextInt();
        for (int i = 1; i <= n; i++) {
            System.out.println("Customer " + i);
            System.out.println("Enter initial balance:");
            double initialBalance = sc.nextDouble();
            System.out.println("Enter annual interest rate (%):");
            double interestRate = sc.nextDouble();
            System.out.println("Enter number of years:");
            int years = sc.nextInt();
            double futureBalance = initialBalance * Math.pow((1 + interestRate / 100), years);
            System.out.println("Future balance after " + years + " years will be: " + futureBalance);
        }
    }
}
