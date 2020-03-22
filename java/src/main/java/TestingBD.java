import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class TestingBD {

	public static void main(String[] args) {

		BigDecimal balance = new BigDecimal("0.85");
		BigDecimal[] countAndRemainder = balance.divideAndRemainder(new BigDecimal("0.25"), new MathContext(0));
		System.out.println(countAndRemainder[0]);
		System.out.println(countAndRemainder[1]);
	}

}
