import java.util.Scanner;

public class Main {

	private static Scanner scan = new Scanner(System.in);

	/**
	 * Allows user to enter two numbers of any size, A and B The program will
	 * then print the sum, difference, product, quotien of A and B.
	 * 
	 * @param args
	 * @throws NotANumberException 
	 */
	public static void main(String[] args) throws NotANumberException {

		BigDecimal bdA = null;
		BigDecimal bdB = null;		
	
		 //check if user is trying to use command line arguments		 
		if (args.length == 2) {
			bdA = new BigDecimal(args[1]);
			bdB = new BigDecimal(args[2]);
		} else { // if not using arguments, prompt user for numbers
	
			getInput(bdA, bdB);
	
		}
	}
	
	private static void getInput(BigDecimal bdA, BigDecimal bdB) throws NotANumberException{
		
		
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

	/**
	 * Determines if the user input is the correct format
	 * 
	 * @param input
	 * @return
	 * @throws NotANumberException 
	 */
	private static boolean checkFormat(String input) throws NotANumberException {
			
		for (int i = 0; i < input.length(); i++) {
			if (Character.isAlphabetic(input.charAt(i))) {
				if(i != 0 || input.charAt(i) != '-'){
					throw new NotANumberException();
				}
			}
		}

		return true;
	}

	/**
	 * The print method which displays the result of each operation
	 */
	private static void printOperations(BigDecimal bd1, BigDecimal bd2) {
	//	System.out.println("hi1");
			System.out.println("Addition yeilds " + bd1.add(bd2));
		System.out.println("subtraction yeilds: " + bd1.subtract(bd2));
	//	System.out.println(bd1.divide(bd2));
		System.out.println(" multiplication yeilds" + bd1.multiply(bd2));
	}

}
