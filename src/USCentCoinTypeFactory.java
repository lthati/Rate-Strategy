import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class USCentCoinTypeFactory extends CoinTypeFactory {
	
	public USCentCoinTypeFactory() {
		validCoins = new ArrayList<Integer>(Arrays.asList(5, 10, 25));
	}
}
