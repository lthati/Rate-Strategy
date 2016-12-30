import java.util.List;

/**
 * 
 * @author leela
 * The factory for creating the objects that configure a pay station
 * for the particular currency
 */

public abstract class CoinTypeFactory {

	protected List<Integer> validCoins;
	
	public boolean isValidCoin(int coinValue)
	{
		if(validCoins.contains(coinValue))
		{
			return true;
		}
		return false;
	}
}
