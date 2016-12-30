/*
  Name - Leela Sravanthi;
  course name - Object Oriented Analysis and Design
  Lab work no. - 10
  email - lthati@student.fitchburgstate.edu
  description of program -
   #1. Paystation Extension
*/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the pay station.
 * 
 * Responsibilities:
 * 
 * 1) Accept payment; 2) Calculate parking time based on payment; 3) Know
 * earning, parking time bought; 4) Issue receipts; 5) Handle buy and cancel
 * events.
 * 
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Department of Computer Science Aarhus University
 * 
 * Please visit http://www.baerbak.com/ for further information.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 */
public class PayStationImpl implements PayStation {
	private int insertedSoFar;
	private int timeBought;
	//creating and initializing HashMap
	private Map<Integer, Integer> coinStore = new HashMap<Integer, Integer>();	
	
	/** the strategy for rate calculations */
	private RateStrategy rateStrategy;
	
	/** the factory that defines currencies */
	private CoinTypeFactory factory;
		
	/**
	 * Construct a pay station.
	 * @param factory currency in which the pay station operates.
	 * @param rateStrategy the rate calculation strategy to use. 
	 */
	public PayStationImpl(CoinTypeFactory factory, RateStrategy rateStrategy)
	{
		this.rateStrategy = rateStrategy;
		this.factory = factory;
	}
	
	//Insert coin into the pay station and adjust state
	public void addPayment(int coinValue) throws IllegalCoinException {
		boolean isValidCoin = factory.isValidCoin(coinValue);
		
		if (!isValidCoin) {
			throw new IllegalCoinException("Invalid coin : " + coinValue);
		}
		
		Integer numberOfCoins = 0; 
		if(coinStore.containsKey(coinValue))
		{
			numberOfCoins = coinStore.get(coinValue);
		}
		numberOfCoins++;
		coinStore.put(coinValue, numberOfCoins);
		insertedSoFar += coinValue;
		timeBought = rateStrategy.calculateTime(insertedSoFar);
		return;
	}
	
   //Read the machine's display
	public int readDisplay() {
		return timeBought;
	}
   //Terminate the ongoing transaction and return a parking receipt.
	public Receipt buy() {
		Receipt r = new ReceiptImpl(timeBought);
		cancel();
		return r;
	}
   //Returning HashMap containing inserted coins so far
	public Map<Integer, Integer> cancel() {
		Map<Integer, Integer> coinMap = new HashMap<Integer, Integer>();
		coinMap.putAll(coinStore);
		coinStore.clear();
		timeBought = insertedSoFar = 0;
		return coinMap;
	}

}
