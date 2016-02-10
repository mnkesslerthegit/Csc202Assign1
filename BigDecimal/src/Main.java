import java.util.Scanner;

public class Main {

	private static Scanner scan = new Scanner(System.in);

	/**
	 * Allows user to enter two numbers of any size, A and B The program will
	 * then print the sum, difference, product, quotien of A and B.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		BigDecimal bdA;
		BigDecimal bdB;
		
	
		 //check if user is trying to use command line arguments		 
		if (args.length == 2) {
			bdA = new BigDecimal(args[1]);
			bdB = new BigDecimal(args[2]);
		} else { // if not using arguments, prompt user for numbers
			String input = "";
			//prompt for first number
			do {
				System.out.println("Enter number 1");
				input = scan.nextLine();
			} while (!checkFormat(input));
			bdA = new BigDecimal(input);
			//prompt for second number
			input = "";
			do {
				System.out.println("Enter number 2");
				input = scan.nextLine();
			} while (!checkFormat(input));
			bdB = new BigDecimal(input);

			//do operations and print results
			printOperations(bdA,bdB);

		}
	}

	/**
	 * Determines if the user input is the correct format
	 * 
	 * @param input
	 * @return
	 */
	private static boolean checkFormat(String input) {
			
		for (int i = 0; i < input.length(); i++) {
			if (Character.isAlphabetic(input.charAt(i))) {
				if(i != 0 || input.charAt(i) != '-'){
				System.out.println("Bad input: try again");
				return false;
				}
			}
		}

		return true;
	}

	/**
	 * The print method which displays the result of each operation
	 */
	private static void printOperations(BigDecimal bd1, BigDecimal bd2) {
		System.out.println(bd1.add(bd2));
	//	System.out.println(bd1.subtract(bd2));
	//	System.out.println(bd1.divide(bd2));
		System.out.println(bd1.multiply(bd2));
	}

}
