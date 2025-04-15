package controlStructure;
import java.util.Scanner;

public class Task5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String password;
        boolean Uppercase = false;
        boolean Digit = false;
        System.out.println("Create your bank password");
        System.out.println("Enter your password:");
        password = sc.nextLine();
        if (password.length() >= 8) {
            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);
                if (c >= 'A' && c <= 'Z') {
                    Uppercase = true;
                }
                if (c >= '0' && c <= '9') {
                    Digit = true;
                }
            }
            if (Uppercase && Digit) {
                System.out.println("Valid password!");
            } else {
                if (!Uppercase) {
                    System.out.println("Password Invalid. Your password doesn't have an uppercase letter.");
                }
                if (!Digit) {
                    System.out.println("Password Invalid. Your password doesn't have a digit.");
                }
            }
        } else {
            System.out.println("Create a password of at least 8 characters!");
        }

    }
}
