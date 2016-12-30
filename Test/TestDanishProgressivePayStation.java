/*
  Name - Leela Sravanthi;
  course name - Object Oriented Analysis and Design
  Lab work no. - 4
  email - sgogula@student.fitchburgstate.edu;lthati@student.fitchburgstate.edu
  description of program -
   #1.Junit for  PaystationImpl
*/
import java.util.HashMap;
import java.util.Map;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Testcases for the Pay Station system.
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
public class TestDanishProgressivePayStation {
	PayStation ps;

	@Before
	public void setUp() {
		ps = new PayStationImpl(new DanishKroneCoinTypeFactory(), new DanishKroneProgressiveRateStrategy());
	}

	/**
	 * Entering 1 Danish Krone should make the display report 6 minutes parking time.
	 */
	@Test
	public void shouldDisplay6MinFor1DanishKrone() throws IllegalCoinException {
		ps.addPayment(1);
		assertEquals("Should display 6 min for 1 Danish Krone", 6, ps.readDisplay());
	}
	
	/**
	 * Entering 2 Danish Krone should make the display report 12 minutes parking time.
	 */
	@Test
	public void shouldDisplay12MinFor2DanishKrone() throws IllegalCoinException {
		ps.addPayment(2);
		assertEquals("Should display 12 min for 2 Danish Krone", 12, ps.readDisplay());
	}

	/**
	 * Entering 5 Danish Krone should make the display report 30 minutes parking time.
	 */
	@Test
	public void shouldDisplay30MinFor5DanishKrone() throws IllegalCoinException {
		ps.addPayment(5);
		assertEquals("Should display 30 min for 5 Danish Krone", 30, ps.readDisplay());
	}

	/**
	 * Entering 10 Danish Krone should make the display report 60 minutes parking time.
	 */
	@Test
	public void shouldDisplay60MinFor10DanishKrone() throws IllegalCoinException {
		ps.addPayment(10);
		assertEquals("Should display 60 min for 10 Danish Krone", 60, ps.readDisplay());
	}
	
	/**
	 * Entering 20 Danish Krone should make the display report 120 minutes parking time.
	 */
	@Test
	public void shouldDisplay120MinFor20DanishKrone() throws IllegalCoinException {
		ps.addPayment(20);
		assertEquals("Should display 120 min for 20 Danish Krone", 120, ps.readDisplay());
	}
	
	/**
	 * Entering 17 Danish Krone should throw IllegalCoinException
	 */
	@Test(expected = IllegalCoinException.class)
	public void shouldRejectIllegalCoin() throws IllegalCoinException {
		ps.addPayment(17);
	}

	/**
	 * Entering 40 Danish Krone should make the display report 20*6+20*5 minutes parking time.
	 */
	@Test
	public void shouldDisplay14MinFor10And25Cents() throws IllegalCoinException {
		ps.addPayment(10);
		ps.addPayment(20);
		ps.addPayment(5);
		ps.addPayment(2);
		ps.addPayment(2);
		ps.addPayment(1);
		assertEquals("Should display 3320 min for 40 Danish Krone", 20*6+20*5, ps.readDisplay());
	}

	/**
	 * buy parking time. terminate the ongoing transaction and return a parking
	 * receipt a non-null object is always returned i.e Should return a receipt
	 * when buy
	 */
	@Test
	public void shouldReturnCorrectReceiptWhenBuy() throws IllegalCoinException {
		ps.addPayment(5);
		ps.addPayment(10);
		ps.addPayment(20);
		Receipt receipt;
		receipt = ps.buy();
		assertNotNull("Receipt reference cannot be null", receipt);
		assertEquals("Receipt value must be 195 min. ", 20*6+15*5, receipt.value());
	}
	/**
	 * To verify whether receipt is displaying correct value of time
	 */
	@Test
	public void shouldStoreTimeInReceipt() {
		Receipt receipt = new ReceiptImpl(30);
		assertEquals("Receipt can store 30 minute value", 30, receipt.value());
	}
	/**
	 * To verify whether receipt is getting returned 
	 */
	@Test
	public void shouldReturnReceiptWhenBuy100DanishKrone() throws IllegalCoinException {
		ps.addPayment(10);
		ps.addPayment(10);
		ps.addPayment(10);
		ps.addPayment(10);
		ps.addPayment(20);
		ps.addPayment(20);
		ps.addPayment(20);
		/*
		 * Map<Integer,Integer> Coins1 = new HashMap<Integer,Integer>();
		 * Coins1=ps.cancel(); //Coins1.put(25, 1); for (Map.Entry<Integer,
		 * Integer> entry : Coins1.entrySet()) { String key =
		 * entry.getKey().toString(); Integer value = entry.getValue();
		 * System.out.println("key, " + key + " value " + value); }
		 */
		Receipt receipt;
		receipt = ps.buy();
		assertEquals(20* 6 + 80*5, receipt.value());
	}
	/**
	 * To verify whether machine is clearing the current transaction after buy 
	 */
	@Test
	public void shouldClearAfterBuy() throws IllegalCoinException {
		ps.addPayment(20);
		ps.buy(); // I do not care about the result
		// verify that the display reads 0
		assertEquals("Display should have been cleared", 0, ps.readDisplay());
		// verify that a following buy scenario behaves properly
		ps.addPayment(10);
		ps.addPayment(20);
		assertEquals("Next add payment should display correct time", 20 * 6 + 10* 5, ps.readDisplay());
		Receipt r = ps.buy();
		assertEquals("Next buy should return valid receipt ", 20 * 6 + 10* 5, r.value());
		assertEquals("Again , display should be cleared", 0, ps.readDisplay());
	}
	/**
	 * To verify whether machine cancel is clearing the current transaction after buy 
	 */
	@Test
	public void shouldClearAfterCancel() throws IllegalCoinException {
		ps.addPayment(10);
		ps.cancel();
		assertEquals("Cancel should clear display", 0, ps.readDisplay());
		ps.addPayment(20);
		assertEquals(" Insert after cancel should work", 20 * 6, ps.readDisplay());
	}
	/**
	 * To verify whether machine is resetting HashMap after Cancel.
	 */
	@Test
	public void testCancelShouldEmptyTheCoinStoreMap() throws IllegalCoinException {
		ps.addPayment(10);
		ps.addPayment(20);
		ps.addPayment(20);
		Map<Integer, Integer> coinMap = ps.cancel();
		assertEquals("coinMap should contain only one 10 coin", Integer.valueOf(1), coinMap.get(10));
		assertEquals("coinMap should contain two 20 coins", Integer.valueOf(2), coinMap.get(20));
		assertEquals("coinMap should not contain any 1 coins", null, coinMap.get(1));
		coinMap = ps.cancel();
		assertEquals("coinMap should not contain 10 coins", null, coinMap.get(10));
		assertEquals("coinMap should not contain 20 coins", null, coinMap.get(20));
		assertEquals("coinMap should not contain 5 coins", null, coinMap.get(5));
	}
	/**
	 * To verify whether cancel is working without adding coins. HashMap is never null even 
	 * if no coins have been inserted
	 */
	@Test
	public void testCancelShouldnotReturnNPE() throws IllegalCoinException {
	
		Map<Integer, Integer> coinMap = ps.cancel();	
		assertEquals("coinMap should not contain 5 coins", null, coinMap.get(5));
		assertEquals("coinMap should not contain 10 coins", null, coinMap.get(10));
		assertEquals("coinMap should not contain 25 coins", null, coinMap.get(20));
		assertTrue(coinMap.isEmpty());
	}
	/**
	 * To verify whether Buy is working after cancel 
	 */
	@Test
	public void testBuyafterCancel() throws IllegalCoinException {
	
		ps.addPayment(20);
		Map<Integer, Integer> coinMap = ps.cancel();
		ps.addPayment(5);
		Receipt r = ps.buy();
		assertEquals("Next buy should return valid receipt ", 5*6, r.value());
		assertEquals("Again , display should be cleared", 0, ps.readDisplay());
		
	}

}
