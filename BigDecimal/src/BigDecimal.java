import java.util.ArrayList;

public class BigDecimal {
	// this list stores as many digits as needed
	private ArrayList<Integer> regular = new ArrayList<>();
	private ArrayList<Integer> decimal = new ArrayList<>();
	public int sign = 1;

	public BigDecimal(String input) {
		if (!input.isEmpty() && input.charAt(0) == '-') {
			sign = -1;
			input = input.substring(1);

		}

		for (int i = 0; i < input.length(); i++) {

			if (input.charAt(i) == '.') {

				for (int q = i + 1; q < input.length(); q++) {

					decimal.add((Integer.parseInt(input.charAt(q) + "")));
				}
				break;

			} else {
				regular.add((Integer.parseInt(input.charAt(i) + "")));
			}

		}

	}

	public BigDecimal(ArrayList<Integer> reg, ArrayList<Integer> dec, int sign) {
		sign = sign;
		regular = reg;
		decimal = dec;
	}

	/**
	 * Creates a backwards version of an arrayList<Integer>
	 * 
	 * @param list
	 * @return
	 */
	private ArrayList<Integer> reverseList(ArrayList<Integer> list) {
		ArrayList<Integer> result = new ArrayList<>();
		for (int i = list.size() - 1; i >= 0; i--) {
			result.add(list.get(i));
		}
		return result;
	}

	/**
	 * Here I simulate doing addition by hand.
	 * 
	 * @param bd2
	 * @return
	 */
	public BigDecimal add(BigDecimal bd2) {

		BigDecimal small = this.smaller(bd2);
		BigDecimal large = this.larger(bd2);

		BigDecimal regular = addHelper(large.regular, small.regular);
		if (small.decimal.size() > large.decimal.size()) {
			addZeroes(large.decimal, small.decimal.size());
		} else {
			addZeroes(small.decimal, large.decimal.size());
		}

		BigDecimal decimal = addHelper(small.decimal, large.decimal);
		// special case where decimals add up to one
		if (decimal.regular.size() > small.decimal.size() && decimal.regular.size() > large.decimal.size()) {
			regular = addHelper(regular.regular, new BigDecimal("1").regular);
			decimal.regular.remove(0);
		}

		BigDecimal result = new BigDecimal(regular.regular, decimal.regular, 1);
		if (large.sign < 0) {
			result.sign = -1;
		}
		System.out.println("sign 1: " + sign + "sign2" + bd2.sign + "   " + result.sign);
		return result;

	}

	/**
	 * Adds zeroes to the decimal array, so that addition runs correctly.
	 * 
	 * @param decimal2
	 * @param size
	 */
	private void addZeroes(ArrayList<Integer> decimal2, int size) {
		for (int i = decimal2.size(); i < size; i++) {
			decimal2.add(0);
		}

	}

	/**
	 * Adds numbers without decimals
	 * 
	 * @param large
	 * @param small
	 * @return
	 */
	private BigDecimal addHelper(ArrayList<Integer> large, ArrayList<Integer> small) {

		ArrayList<Integer> rA = reverseList(small);
		ArrayList<Integer> rB = reverseList(large);
		String result = "";

		int overflow = 0;
		int i = 0;
		while (i < small.size()) {
			int sum = rA.get(i) + rB.get(i) + overflow;
			overflow = sum / 10;

			result = (sum % 10) + result;

			i++;

		}

		while (i < large.size()) {
			int sum = (rB.get(i)) + overflow;
			overflow = sum / 10;

			result = (sum % 10) + result;

			i++;
		}
		if (overflow > 0) {
			result = overflow + result;
		}

		return new BigDecimal(result);

	}

	/**
	 * Returns the compliment of a number
	 * 
	 * @param arg
	 *            the number to take the compliment of
	 * @param size
	 *            a string of the desired size of the compliment
	 * @return
	 */
	private ArrayList<Integer> compliment(ArrayList<Integer> arg, int size) {
		ArrayList<Integer> result = new ArrayList<>();

		if (arg.size() < size) {
			int diff = size - arg.size();
			for (int i = 0; i < diff; i++) {
				result.add(9);
			}
		} else {

		}
		for (int i = 0; i < arg.size(); i++) {
			if (i == arg.size() - 1) {
				result.add(10 - arg.get(i));
			} else {
				result.add(9 - arg.get(i));
			}

		}
		return result;
	}

	/**
	 * Subtracts B from A.
	 * 
	 * @param A
	 * @param B
	 * @return
	 */
	public BigDecimal subtract(BigDecimal B) {

		BigDecimal result;
		// subtracting negative from positive is adding
		if (sign > 0 && B.sign < 0) {

			BigDecimal C = new BigDecimal(B.regular, B.decimal, 1);
			return this.add(C);
			// subtracting positive from negative is adding
		} else if (sign < 0 && B.sign > 0) {
			BigDecimal C = new BigDecimal(regular, decimal, 1);
			result = add(C);
			result.sign = -1;
			return result;
			// add compliment, and subtract excess
		} else {
			// System.out.println(compliment(B.regular, regular.size()) + "
			// one");
			// System.out.println(compliment(B.decimal, decimal.size()) + "
			// two");
			
			ArrayList<Integer> largeDecimal = largerArray(decimal, B.decimal);
			ArrayList<Integer> largeregular = largerArray(decimal, B.decimal);
			
			BigDecimal C = new BigDecimal(compliment(B.regular, regular.size()), compliment(B.decimal, decimal.size()),
					1);

			C = this.add(C);
			if (!C.regular.isEmpty()) {
				C.regular.remove(0);
			}
			
			if (!(sign > 0 && B.sign > 0)) {
				C.sign = -1;
			}
			if (C.decimal.get(0) + decimal.get(0) >= 10) {
				C.regular.set(0, C.regular.get(0) - 1);
			}
			System.out.println("C " + C);
			return C;
		}

	}
	
	private ArrayList<Integer> largerArray(ArrayList<Integer> A, ArrayList<Integer> B){
		int i = A.size();
		if(i > B.size()){
			i = B.size();
		}
		for(int q = 0; q < i; q++){
			if(A.get(q) > B.get(q)){
				return A;
			}else if(B.get(q) > A.get(q)){
				return B;
			}
				
			
		}
		return A;
		
	}

	public String toString() {
		String result = "";
		if (sign < 0) {
			result = "-" + result;
		}
		for (int i = 0; i < regular.size(); i++) {
			result += regular.get(i);

		}
		result += ".";
		for (int i = 0; i < decimal.size(); i++) {
			result += decimal.get(i);

		}
		return result;
	}

	/**
	 * Check to see if one big decimal is greater than another
	 * 
	 * @param other
	 * @return
	 */
	public boolean greaterMagnitude(BigDecimal other) {
		if (other.regular.size() < regular.size()) {
			return true;
		} else if (other.regular.size() > regular.size()) {
			return false;

		}
		return false;
	}

	/**
	 * returns the larger bigDecimal
	 * 
	 * @param other
	 * @return
	 */
	private BigDecimal larger(BigDecimal other) {
		if (greaterMagnitude(other)) {
			return this;
		} else {
			return other;
		}

	}

	/**
	 * returns the smaller bigDecimal
	 * 
	 * @param other
	 * @return
	 */
	private BigDecimal smaller(BigDecimal other) {
		if (greaterMagnitude(other)) {
			return other;
		} else {
			return this;
		}

	}

	public BigDecimal multiply(BigDecimal bd2) {
		BigDecimal result = new BigDecimal("0");
		int i = regular.size() + decimal.size();
		int q = bd2.regular.size() + bd2.decimal.size();
		// System.out.println("I: " + i + " q " + q);
		Integer ival;
		Integer qval;
		while (i > 0) {

			while (q > 0) {
				int iPower = regular.size() - i;
				int qPower = bd2.regular.size() - q;
				// decide what list the number being multiplied comes from
				// (regular or decimal)
				if (iPower >= 0) {
					ival = regular.get(i - 1);
				} else {
					ival = decimal.get(-iPower - 1);
				}
				if (qPower >= 0) {
					qval = bd2.regular.get(q - 1);
				} else {
					qval = bd2.decimal.get(-qPower - 1);
				}

				BigDecimal temp = new BigDecimal(bigMultiply(ival, iPower, qval, qPower));
				result = result.add(temp);
				// System.out.println(result);
				q--;
			}
			q = bd2.regular.size() + bd2.decimal.size();
			i--;

		}
		return result;
	}

	private BigDecimal multiplyHelper(ArrayList<Integer> A, ArrayList<Integer> B) {
		BigDecimal result = new BigDecimal("");
		for (int i = 0; i < A.size(); i++) {
			for (int q = 0; q < B.size(); q++) {
				BigDecimal temp = new BigDecimal(bigMultiply(A.get(i), A.size() - i - 1, B.get(q), B.size() - q - 1));
				result = result.add(temp);
			}
		}

		return result;

	}

	/**
	 * Multiplies two single digits raised to an arbitrary power of ten
	 * 
	 * @param integer
	 *            The first number
	 * @param i
	 *            The power of ten for the first number
	 * @param integer2
	 *            The second number
	 * @param q
	 *            The power of Ten for the second Number
	 * @return
	 */
	private String bigMultiply(Integer integer, int i, Integer integer2, int q) {
		// System.out.println(integer + " " + i + " " + integer2 + " " + q);

		int zeroes = i + q;
		int product = integer * integer2;
		String result = "" + product;
		if (zeroes > 0) {
			// System.out.println(result);
			for (int z = 0; z < zeroes; z++) {
				result += "0";
			}
			System.out.println("bigMultiply: " + result);
			return result;
		} else if (zeroes < 0) {
			int z = -1;
			if (product >= 10) {
				z--;
			}
			while (z > zeroes) {
				result = "0" + result;
				z--;
			}
			if (product >= 10 && zeroes == -1) {
				result = result.charAt(0) + "." + result.substring(1);
			} else {
				result = "." + result;
			}
			System.out.println("bigMultiply: " + result);
			return result;
		} else {
			System.out.println("bigMultiply: " + result);
			return result;
		}
	}

}
