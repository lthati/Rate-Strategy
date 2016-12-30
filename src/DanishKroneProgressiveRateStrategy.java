
public class DanishKroneProgressiveRateStrategy implements RateStrategy{

	@Override
	public int calculateTime(int amount) {
		if(amount * 6 > 120)
		{
			amount = amount - 20;
			return (20*6) + (amount*5);
		}
		return amount * 6;
	}

}
