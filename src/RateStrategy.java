
/**
 * 
 * @author leela
 * The strategy for calculating parking rates.
 */
public interface RateStrategy {
	/**
	 * @return the number of minutes parking time
	 * @param amount payment in some currency
	 */
	public int calculateTime(int amount);
}
