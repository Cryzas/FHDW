package model.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Account;
import model.Transfer;
import model.UnderLimitException;

public class TransactionTest {

	Transfer transfer1;
	Transfer transfer2;
	Transfer transfer3;
	Transfer transfer4;
	Transfer transfer5;
	Transfer transfer6;
	Transfer transfer7;
	Transfer nullbuchung;
	Transfer selbstBuchung;
	Transfer negativeBuchung;

	Account dealer;
	Account junkie;
	Account cook;

	@Before
	public void setUp() {

		dealer = Account.create("Drogenkonto");
		junkie = Account.create("Sparbuch");
		cook = Account.create("WaltersKonto");

		transfer1 = Transfer.create(junkie, dealer, 500, "Koks 100 gram");
		transfer2 = Transfer.create(junkie, dealer, 50, "Koks 10 gram");
		transfer3 = Transfer.create(junkie, dealer, 5, "Koks 1 gram");
		transfer4 = Transfer.create(junkie, dealer, 10, "Cannabis 1 gram");
		transfer5 = Transfer.create(junkie, dealer, 100, "Cannabis 10 gram");
		transfer6 = Transfer.create(dealer, cook, 1000, "Meth 1000 gram");
		transfer7 = Transfer.create(dealer, cook, 100, "LSD 10 gram");
		nullbuchung = Transfer.create(junkie, dealer, 0, "Trolled");
		selbstBuchung = Transfer.create(junkie, junkie, 1, "Ich hab keine Freunde");
		negativeBuchung = Transfer.create(dealer, junkie, -500, "Lastschrift");
	}

	@Test
	public void testNullBuchung() throws UnderLimitException {
		assertEquals(0, junkie.entrySize());
		assertEquals(0, dealer.entrySize());
		assertEquals(0, dealer.getBalance());
		assertEquals(0, junkie.getBalance());
		nullbuchung.book();
		assertEquals(1, junkie.entrySize());
		assertEquals(1, dealer.entrySize());
		assertEquals(0, dealer.getBalance());
		assertEquals(0, junkie.getBalance());
	}

	@Test
	public void testNegativeBuchung() throws UnderLimitException {
		assertEquals(0, junkie.entrySize());
		assertEquals(0, dealer.entrySize());
		assertEquals(0, dealer.getBalance());
		assertEquals(0, junkie.getBalance());
		negativeBuchung.book();
		assertEquals(1, junkie.entrySize());
		assertEquals(1, dealer.entrySize());
		assertEquals(500, dealer.getBalance());
		assertEquals(-500, junkie.getBalance());
	}

	@Test
	public void testSingleTransferOneWay() throws UnderLimitException {
		transfer1.book();
		assertEquals(500, dealer.getBalance());
		assertEquals(-500, junkie.getBalance());
	}

	@Test
	public void testMultipleTransfersOneWay() throws UnderLimitException {
		assertEquals(0, junkie.entrySize());
		assertEquals(0, dealer.entrySize());
		assertEquals(0, dealer.getBalance());
		assertEquals(0, junkie.getBalance());
		transfer1.book();
		assertEquals(500, dealer.getBalance());
		assertEquals(-500, junkie.getBalance());
		assertEquals(1, junkie.entrySize());
		assertEquals(1, dealer.entrySize());
		transfer2.book();
		assertEquals(550, dealer.getBalance());
		assertEquals(-550, junkie.getBalance());
		assertEquals(2, junkie.entrySize());
		assertEquals(2, dealer.entrySize());
		transfer3.book();
		assertEquals(555, dealer.getBalance());
		assertEquals(-555, junkie.getBalance());
		assertEquals(3, junkie.entrySize());
		assertEquals(3, dealer.entrySize());
		transfer4.book();
		transfer5.book();
		assertEquals(665, dealer.getBalance());
		assertEquals(-665, junkie.getBalance());
		assertEquals(5, junkie.entrySize());
		assertEquals(5, dealer.entrySize());

	}

	@Test
	public void testMultipleTransfers() throws UnderLimitException {
		transfer1.book();
		assertEquals(1, junkie.entrySize());
		assertEquals(1, dealer.entrySize());
		assertEquals(500, dealer.getBalance());
		assertEquals(-500, junkie.getBalance());
		transfer6.book();
		assertEquals(1, junkie.entrySize());
		assertEquals(1, cook.entrySize());
		assertEquals(2, dealer.entrySize());
		assertEquals(-500, dealer.getBalance());
		assertEquals(-500, junkie.getBalance());
		assertEquals(1000, cook.getBalance());
	}

	@Test
	public void testUnderLimit() {
		try {
			assertFalse(transfer6.isTried());
			assertEquals(0, transfer6.getTriedCount());
			transfer6.book();
			assertTrue(transfer6.isTried());
			assertEquals(1, transfer6.getTriedCount());
			transfer6.book();
			assertTrue(transfer6.isTried());
			assertEquals(2, transfer6.getTriedCount());
			transfer6.book();
			assertTrue(transfer6.isTried());
			assertEquals(3, transfer6.getTriedCount());
			transfer6.book();
			assertTrue(transfer6.isTried());
			assertEquals(4, transfer6.getTriedCount());
			transfer6.book();
			assertTrue(transfer6.isTried());
			assertEquals(5, transfer6.getTriedCount());
		} catch (UnderLimitException e) {
			System.out.println("true");
		}
	}

}
