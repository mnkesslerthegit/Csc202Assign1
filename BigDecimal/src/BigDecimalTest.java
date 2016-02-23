import org.junit.Test;

/**
 * I used this test to develop the multiplication method
 * @author Max
 *
 */
public class BigDecimalTest {

	@Test
	public final void test() {
		int k = 1;
		int d = 1;
		for(double i = -k; i < k; i = i + d){
			for(double q = -k; q < k; q = q + d){
				BigDecimal a = new BigDecimal("" + i);
				BigDecimal b = new BigDecimal("" + q);
				double difference = Double.parseDouble(a.multiply(b).toString()) - (i * q);
				if(difference > 1 && i > 0 && q > 0){
				System.out.println("i: " + i + "q: " + q + " difference: " + difference);
				}
			}
			
			
		}
	}

}
