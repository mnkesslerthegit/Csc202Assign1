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
		// sign = sign;
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

		//System.out.println("Signs:" + sign + "   " + bd2.sign);
		if(sign > 0 && bd2.sign < 0){
			BigDecimal positive = new BigDecimal(bd2.regular,bd2.decimal, 1);
			
			return this.subtract(positive);
			
		}
		
		if(sign < 0 && bd2.sign > 0){
			BigDecimal positive = new BigDecimal(this.regular,this.decimal, 1);
			System.out.println("help" + positive.sign);
			return bd2.subtract(positive);
		}
		
		// find and organize each of the 4 arrays;
		ArrayList<Integer> bigReg = largerArray(regular, bd2.regular, false);
		ArrayList<Integer> bigDec = largerArray(decimal, bd2.decimal, true);
		ArrayList<Integer> smallReg;
		ArrayList<Integer> smallDec;
		if (bigReg == regular) {
			smallReg = bd2.regular;
		} else {
			smallReg = regular;
		}

		if (bigDec == decimal) {
			smallDec = bd2.decimal;
		} else {
			smallDec = decimal;
		}

		// add the regular arrays
		BigDecimal regular = addHelper(bigReg, smallReg);

		// add zeroes to the decimal with less digits
		if (smallDec.size() > bigDec.size()) {
			addZeroes(bigDec, smallDec.size());
		} else {
			addZeroes(smallDec, bigDec.size());
		}

		// add the decimal arrays
		BigDecimal decimal = addHelper(smallDec, bigDec);
		// special case where decimals add up to one
		if (decimal.regular.size() > smallDec.size() && decimal.regular.size() > bigDec.size()) {
			regular = addHelper(regular.regular, new BigDecimal("1").regular);
			decimal.regular.remove(0);
		}

		BigDecimal result = new BigDecimal(regular.regular, decimal.regular, 1);

		if(sign < 1 && bd2.sign < 1){
			result.sign = -1;
		}
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
		while (i < small.size() && !rA.isEmpty() && !rB.isEmpty()) {
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
		if (arg.isEmpty()) {
			return result;
		}
		
		

		int last = 0;
		for (int i = 0; i < arg.size(); i++) {
			if (arg.get(i) == 0) {
				continue;
			}
			last = i;
			result.add(9 - arg.get(i));

		}
		if (!result.isEmpty()) {
			result.set(last, 10 - arg.get(last));
		}
		
		//add on any nines
		if (arg.size() < size) {
			int diff = size - arg.size();
			for (int i = 0; i < diff; i++) {
				result.add(0, 9);
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
		// System.out.println("Signs: " + sign + " " + B.sign);
		BigDecimal result;
		// subtracting negative from positive is adding
		if (sign > 0 && B.sign < 0) {

			BigDecimal C = new BigDecimal(B.regular, B.decimal, 1);
			return C.add(this);
			// subtracting positive from negative is adding
		} else if (sign < 0 && B.sign > 0) {
			BigDecimal C = new BigDecimal(regular, decimal, 1);
			result = C.add(B);
			result.sign = -1;
			return result;
			// subtracting two positive numbers or two negative numbers: 
		} else  {

			// find the result of the decimal values first:
			// start by finding the larger decimal
			ArrayList<Integer> largeDec = largerArray(decimal, B.decimal, true);
			ArrayList<Integer> smallDec;
			//assume our decimal is larger
			boolean decLarger = true;
			if (largeDec == decimal) {
				smallDec = B.decimal;
				System.out.println("this is wrong" + decimal + "  bigger than " + B.decimal);
			} else {
				smallDec = decimal;
				//our decimal is not larger
				decLarger = false;
			}

			// now find the larger regular values;

			ArrayList<Integer> largeReg = largerArray(regular, B.regular, false);
			ArrayList<Integer> smallReg;
		//	boolean regLarger = true;
			if (largeReg == regular) {
				smallReg = B.regular;
			} else {
				smallReg = regular;
			//	regLarger = false;
			}

			System.out.println("In order: large reg, small reg, large dec, small dec");
			System.out.println(largeReg);
			System.out.println(smallReg);
			System.out.println(largeDec);
			System.out.println(smallDec);

			// subtract the smaller values from the greater values
			BigDecimal C = new BigDecimal(largeReg, largeDec, 1);
		//	ArrayList<Integer> smallRegComp = compliment(smallReg, smallReg.size());
	//		ArrayList<Integer> smallDecComp = compliment(smallDec, smallDec.size());
			int magnitude = smallReg.size();
			if (largeReg.size() > magnitude){
				magnitude = largeReg.size();
			}
			BigDecimal D = new BigDecimal(compliment(smallReg, magnitude), compliment(smallDec, smallDec.size()),
					1);

			System.out.println("C: " + C);
			System.out.println("D: " + D);
			BigDecimal E = D.add(C);
			System.out.println("E: " + E);
			if (!C.regular.isEmpty() && !D.regular.isEmpty()) {
				// remove the one which should be added via the complement
				// method
				E.regular.remove(0);
			}
			// set the sign

			// if we subtracted decimals, we added one. We remove the one by
			// subtracting again.
			if (!B.decimal.isEmpty()) {
				System.out.println("E so far:" + E);
				E.regular = subtractOne(E.regular);
				System.out.println("E so far:" + E);
				if(!decLarger){
				//	E.regular = subtractOne(E.regular);
					System.out.println("E so far:" + E);
					System.out.println("why not?           sdfsdf");
					E.decimal = compliment(E.decimal, E.decimal.size());
					System.out.println("E so far:" + E);
				}
			}

		

			

			E.sign = greaterValue(this, B);
			// System.out.println("C " + C);
			return E;
		}
		

	}
	
	
	private ArrayList<Integer> subtractOne(ArrayList<Integer> list){
		ArrayList<Integer> result = new ArrayList<>();
		for(int i = 0; i < list.size(); i++){
			result.add(9);
		}
		result = addHelper(result, list).regular;
		
		result.remove(0);
		return result;
		
	}

	/**
	 * Returns the larger array assuming the array holds a number Whether the
	 * number comes after the decimal must be specified.
	 * 
	 * @param A
	 * @param B
	 * @return
	 */
	private ArrayList<Integer> largerArray(ArrayList<Integer> A, ArrayList<Integer> B, boolean decimal) {
		// s becomes the smaller size,
		// and l becomes the larger size

		int s = A.size();
		int l = B.size();
		ArrayList<Integer> greaterMag = B;
		// ArrayList<Integer> smallerMag = B;

		// we guess that B is larger. If not, switch s and l
		if (s > B.size()) {
			s = B.size();
			l = A.size();
			greaterMag = A;
			// smallerMag = A;
		}
		if (decimal) { // decimal case:
			// we check to see if any digit is larger than the other
			for (int q = 0; q < s; q++) {
				if (A.get(q) > B.get(q)) {
					return A;
				} else if (B.get(q) > A.get(q)) {
					return B;
				}

			}

		} else { // regular case:
					// if a number has more digits, the only way it is smaller
					// is leading zeroes
			int i = 0;
			for (i = 0; i < (l - s); i++) {
				if (!greaterMag.isEmpty() && greaterMag.get(i) != 0) {
					return greaterMag;
				}
			}

			// Now we check for a greater digit
			for (int q = i; q < greaterMag.size(); q++) {
				if (A.get(q) > B.get(q)) {
					return A;
				} else if (B.get(q) > A.get(q)) {
					return B;
				}
			}

		}
		if(A.isEmpty()){
			return B;
		}
		return A;
	}

	/**
	 * returns 1 if A is greater than B, 0 if they are equal, or else -1
	 * 
	 * @param A
	 * @param B
	 * @return
	 */
	private int greaterValue(BigDecimal A, BigDecimal B) {
		ArrayList<Integer> lReg = largerArray(A.regular, B.regular, false);
		ArrayList<Integer> lDec = largerArray(A.decimal, B.decimal, false);
		// check if A regular is larger
		if (lReg == A.regular && !lReg.equals(B.regular)) {
			return 1;

			// check if B regular is larger
		} else if (lReg == B.regular && !lReg.equals(A.regular)) {
			return -1;
		}

		// check if A decimal is larger
		if (lDec == A.decimal && !lReg.equals(B.decimal)) {
			return 1;
			// check if B decimal is larger
		} else if (lDec == B.decimal && !lReg.equals(A.regular)) {
			return -1;
		}

		// otherwise, they are completely the same

		return 0;

	}

	/**
	 * Print a big decimal
	 */
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
	// public boolean greaterMagnitude(BigDecimal other) {
	// if (other.regular.size() < regular.size()) {
	// return true;
	// } else if (other.regular.size() > regular.size()) {
	// return false;
	//
	// }
	// return false;
	// }
	//
	// /**
	// * returns
	// *
	// * @param other
	// * @return
	// */
	// private BigDecimal larger(BigDecimal other) {
	// if (greaterMagnitude(other)) {
	// return this;
	// } else {
	// return other;
	// }
	//
	// }
	//
	// /**
	// * returns the smaller bigDecimal
	// *
	// * @param other
	// * @return
	// */
	// private BigDecimal smaller(BigDecimal other) {
	// if (greaterMagnitude(other)) {
	// return other;
	// } else {
	// return this;
	// }
	//
	// }

	/**
	 * Multipies big decimals
	 * 
	 * @param bd2
	 * @return
	 */
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
	//
	// private BigDecimal multiplyHelper(ArrayList<Integer> A,
	// ArrayList<Integer> B) {
	// BigDecimal result = new BigDecimal("");
	// for (int i = 0; i < A.size(); i++) {
	// for (int q = 0; q < B.size(); q++) {
	// BigDecimal temp = new BigDecimal(bigMultiply(A.get(i), A.size() - i - 1,
	// B.get(q), B.size() - q - 1));
	// result = result.add(temp);
	// }
	// }
	//
	// return result;
	//
	// }

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
			// System.out.println("bigMultiply: " + result);
			return result;
		} else {
			// System.out.println("bigMultiply: " + result);
			return result;
		}
	}

}
