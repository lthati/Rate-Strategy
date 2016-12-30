import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DanishKroneCoinTypeFactory extends CoinTypeFactory {

	public DanishKroneCoinTypeFactory()
	{
		validCoins = new ArrayList<Integer>(Arrays.asList(1, 2, 5, 10, 20));
	}

}
