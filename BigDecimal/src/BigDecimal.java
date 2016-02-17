import java.util.ArrayList;

public class BigDecimal {
	// this list stores as many digits as needed
	private ArrayList<Integer> myList = new ArrayList<>();
	public int sign = 1;
	public BigDecimal(String input) {
		if(!input.isEmpty() && input.charAt(0) == '-'){
			sign = -1;
			input = input.substring(1);
			
		}
		
		for (int i = 0; i < input.length(); i++) {
			{
			//	System.out.println("input at " + i +" is: "+ input.charAt(i));
				myList.add(Integer.parseInt("" + input.charAt(i)));
			}
		}
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
		BigDecimal small = smaller(bd2);
		BigDecimal large = larger(bd2);
		ArrayList<Integer> rA = reverseList(small.myList);
		ArrayList<Integer> rB = reverseList(large.myList);
		String result = "";

		int overflow = 0;
		int i = 0;
		while (i < small.myList.size()) {
			int sum = rA.get(i)  + rB.get(i)+ overflow;
			overflow = sum / 10;
			
				
			result = (sum % 10) + result;
			
			i++;

		}

		while (i < large.myList.size()) {
			int sum = (rB.get(i) * large.sign)  + overflow;
			overflow = sum / 10;
			if(small.sign != large.sign && sum < 0){
				result = (sum % 10) + 10 + result;
				overflow -= 1;
			}else{
			result = (sum % 10) + result;
			}
			i++;
		}
		if (overflow > 0) {
			result = overflow + result;
		}
		
		if(sign < 0 && bd2.sign < 0){
			return new BigDecimal("-" + result);
		}else{
		return new BigDecimal(result);
		}

	}
	
	/**
	 * Returns the compliment of a number
	 * @param arg the number to take the compliment of
	 * @param size a string of the desired size of the compliment
	 * @return
	 */
	private ArrayList<Integer> compliment(ArrayList<Integer> arg, int size){
		ArrayList<Integer> result = new ArrayList<>();
		
		if(arg.size() < size){
			int diff = size - arg.size();
			for(int i = 0; i < diff; i++){
				result.add(9);
			}
		}else{
			
		}
		for(int i = 0; i < arg.size(); i++){
			result.add(9- arg.get(i));
			
		}
		return result;
	}

	/**
	 * Subtracts B from A.
	 * @param A
	 * @param B
	 * @return
	 */
	public BigDecimal subtract(BigDecimal A, BigDecimal B){
		
		BigDecimal result;
		//subtracting negative from positive is adding
		if(A.sign > 0 && B.sign < 0){
			return A.add(B);
			//subtracting positive from negative is adding
		}else if(A.sign < 0 && B.sign > 0){
			return A.add(B);
			//add compliment, and subtract excess
		}else if(A.sign > 0 &&  B.sign > 0){
			
		}
		
		
		
		BigDecimal large = A.larger(B);
		
		
		
		
		return large;
		
		
	}
	
	public String toString() {
		return myList.toString();
	}
	
	
	

	/**
	 * Check to see if one big decimal is greater than another
	 * 
	 * @param other
	 * @return
	 */
	public boolean greaterMagnitude(BigDecimal other) {
		if (other.myList.size() < myList.size()) {
			return true;
		} else if (other.myList.size() > myList.size()) {
			return false;

		}

		for (int i = 0; i < other.myList.size(); i++) {
			if (myList.get(i) > other.myList.get(i)) {
				return true;
			} else if (myList.get(i) < other.myList.get(i)) {
				return false;
			}
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

	public BigDecimal subtract(BigDecimal bd2) {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal divide(BigDecimal bd2) {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal multiply(BigDecimal bd2) {
		BigDecimal result = new BigDecimal("");

		for (int i = 0; i < myList.size(); i++) {
			for (int q = 0; q < bd2.myList.size(); q++) {
				BigDecimal temp = new BigDecimal(bigMultiply(myList.get(i),
						myList.size() - i - 1, bd2.myList.get(q),
						bd2.myList.size() - q - 1));
				result = result.add(temp);
			}
		}
		return result;
	}

	private String bigMultiply(Integer integer, int i, Integer integer2, int q) {
		int zeroes = i + q;
		int product = integer * integer2;
		String result = "" + product;
	//	System.out.println(result);
		for (int z = 0; z < zeroes; z++) {
			result += "0";
		}
		return result;
	}

}
