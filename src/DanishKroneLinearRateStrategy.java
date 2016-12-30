
public class DanishKroneLinearRateStrategy implements RateStrategy{

	@Override
	public int calculateTime(int amount) {
		return amount*7;
	}
}
