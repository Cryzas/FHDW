package test;
/*
 * TEST
 */

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Before;

import model.Automaton;
import model.State;

public class Test {

	private Automaton a1;
	private State a1_z0, a1_z1, a1_z2;

	private Automaton a2;
	private State a2_z0, a2_z1, a2_z2, a2_z3;

	private Automaton a3;
	private State a3_z0, a3_z1, a3_z2, a3_z3, a3_z4, a3_z5;

	@Before
	/**
	 * Erstellt die Konfigurationen der zu testenen Automaten <br>
	 * 
	 */
	public void setUpAutomatonConfiguration() throws Exception {

		/*
		 * Automat 1 erstellt den Automaten aus Dokument Automat1.pdf
		 */
		a1_z0 = State.create("z0");
		a1_z1 = State.create("z1");
		a1_z2 = State.create("z2");
		a1_z0.add('1', 'a', a1_z1);
		a1_z1.add('0', 'b', a1_z1);
		a1_z1.add('1', 'c', a1_z2);
		a1 = Automaton.create(a1_z0, a1_z2);

		/*
		 * Automat 2 erstellt den Automaten aus Dokument Automat2.pdf
		 */
		a2_z0 = State.create("z0");
		a2_z1 = State.create("z1");
		a2_z2 = State.create("z2");
		a2_z3 = State.create("z3");
		a2_z0.add('0', 'a', a2_z1);
		a2_z1.add('1', 'b', a2_z0);
		a2_z1.add('1', 'c', a2_z1);
		a2_z1.add('0', 'd', a2_z2);
		a2_z2.add('1', 'e', a2_z1);
		a2_z2.add('1', 'f', a2_z2);
		a2_z2.add('2', 'g', a2_z3);
		a2 = Automaton.create(a2_z0, a2_z3);

		/*
		 * Automat 3 erstellt den Automaten aus Dokument
		 * Automat_des_todes.pdf.pdf
		 */
		a3_z0 = State.create("z0");
		a3_z1 = State.create("z1");
		a3_z2 = State.create("z2");
		a3_z3 = State.create("z3");
		a3_z4 = State.create("z4");
		a3_z5 = State.create("z5");
		a3_z0.add('0', 'a', a3_z1);
		a3_z1.add('1', 'b', a3_z0);
		a3_z1.add('1', 'd', a3_z1);
		a3_z1.add('1', 'e', a3_z2);
		a3_z1.add('3', 'x', a3_z4);
		a3_z2.add('0', 'c', a3_z1);
		a3_z2.add('1', 'f', a3_z2);
		a3_z2.add('2', 'g', a3_z3);
		a3_z2.add('1', 'k', a3_z5);
		a3_z4.add('1', 'j', a3_z4);
		a3_z4.add('1', 'y', a3_z1);
		a3_z4.add('0', 'm', a3_z5);
		a3_z5.add('1', 'i', a3_z4);
		a3_z5.add('3', 'l', a3_z2);
		a3_z5.add('1', 'h', a3_z5);
		a3 = Automaton.create(a3_z0, a3_z3);

	}

	@org.junit.Test
	public void testAutomat1() {
		Assert.assertEquals(a1.run("11"), "ac");
		Assert.assertEquals(a1.run("101"), "abc");
		Assert.assertEquals(a1.run("1000001"), "abbbbbc");
		Assert.assertEquals(a1.run("110"), "");
		Assert.assertEquals(a1.run("111"), "");
		Assert.assertEquals(a1.run("01"), "");
		Assert.assertEquals(a1.run("110"), "");
	}

	@org.junit.Test
	public void testAutomat2() {
		Assert.assertEquals(a2.run("01002"), "abadg");
		Assert.assertEquals(a2.run("002"), "adg");
		Assert.assertEquals(a2.run("0011002"), "adebadg");
		Assert.assertEquals(a2.run("00102"), "adedg");
		Assert.assertEquals(a2.run("00112"), "adffg");
		Assert.assertEquals(a2.run("0102"), "acdg");
		Assert.assertEquals(a2.run("01111102"), "acccccdg");
		Assert.assertEquals(a2.run("011111002"), "accccbadg");
		Assert.assertEquals(a2.run("0111102"), "accccdg");
		Assert.assertEquals(a2.run("011111002"), "accccbadg");
	}

	@org.junit.Test
	public void testAutomatDesTodesSimpel() {
		Assert.assertEquals(a3.run("012"), "aeg");
		Assert.assertEquals(a3.run("0130101312"), "adxmimhlfg");
		Assert.assertEquals(a3.run("013013012"), "adxmhlceg");

	}

	@org.junit.Test
	public void testAutomatDesTodes5Stellen() {

		/**
		 * TODO: Iterator in Methode auslagern Mittels der Collection
		 * <possibleOutputs> werden die moeglichen Ausgaben via Iterator
		 * ueberprueft und für eine spezielle eingabe alle moeglichen Ergebnisse
		 * auf True ueberprueft
		 **/
		Collection<String> possibleOutputs = new LinkedList<>();
		possibleOutputs.add("aeffg");
		possibleOutputs.add("adefg");
		possibleOutputs.add("addeg");

		boolean wordMatch = false;
		String realOutput = this.a3.run("01112");
		Iterator<String> it = possibleOutputs.iterator();
		while (it.hasNext()) {
			String oneOutput = it.next();
			if (oneOutput.equals(realOutput)) {
				wordMatch = true;
				break;
			}
		}
		assertEquals(true, wordMatch);

	}

	@org.junit.Test
	public void testAutomatDesTodes7Stellen() {

		Collection<String> possibleOutputs = new LinkedList<>();
		possibleOutputs.add("aeffffg");
		possibleOutputs.add("adefffg");
		possibleOutputs.add("addeffg");
		possibleOutputs.add("adddefg");
		possibleOutputs.add("addddeg");

		boolean wordMatch = false;
		String realOutput = this.a3.run("0111112");
		Iterator<String> it = possibleOutputs.iterator();
		while (it.hasNext()) {
			String oneOutput = it.next();
			if (oneOutput.equals(realOutput)) {
				wordMatch = true;
				break;
			}
		}
		assertEquals(true, wordMatch);

	}

}
