package controlStructure;
import java.util.*;

public class Task1 {
	public static void main(String args[]) {	
	int creditScore;
	float annualIncome;
	Scanner sc=new Scanner(System.in);
	System.out.println("Enter Credit score");
	creditScore=sc.nextInt();
	System.out.println("Enter Annual Income");
	annualIncome=sc.nextInt();
	if (creditScore > 700 && annualIncome >= 50000) {
		System.out.println("Eligible for loan!");
	}
	else {
		System.out.println("Sorry! you are not eligible for loan!");
	}
   }
}
