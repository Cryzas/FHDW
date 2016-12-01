package model;

/**
 * Steht fuer das Modell eines endliche Automaten mit Ausgabe aus der
 * Theoretischen Inforatik <br>
 * Hat genau einen Start-<startState> und einen Endzustand<endState>
 **/
public class Automaton {

	// Start und Endzustaende des jeweiligen Automaten
	private State startState;
	private State endState;

	/**
	 * Methode zum erstellen eines Automaten<br>
	 * Benoetigt einen Start-<startState> und einen Endzustand <endState><br>
	 * Prueft ob Startzustand ungleich Endzustand ist
	 * 
	 * @throws AutomatonConstructionExeption
	 **/
	public static Automaton create(State startState, State endState) throws AutomatonConstructionExeption {
		if (startState.equals(endState))
			throw new AutomatonConstructionExeption();
		return new Automaton(startState, endState);
	}

	/**
	 * Initialisiert die Parameter <startState> und <endState> des Automaten
	 **/
	private Automaton(State startState, State endState) {
		this.startState = startState;
		this.endState = endState;
	}

	/**
	 * Methode zum ausfuehren des Automaten <br>
	 * Benoetigt den Parameter <input> als Eingabe fuer den Automaten Erstellt
	 * einen neuen Manager <manager><br>
	 * Gibt den Parameter <output> als Ausgabe des Automaten zurueck und zeigt
	 * diesen an
	 **/
	public String run(String input) {
		Manager manager = new Manager();
		Configuration.create(Automaton.this, startState, input, "", manager).step();
		String output = manager.getOutput();
		System.out.println("Output " + output);
		return output;
	}

	/** Getter um Startzustand <startState> auszuwerfen **/
	public State getStart() {
		return startState;
	}

	/** Getter um Endzustand <endState> auszuwerfen **/
	public State getEnd() {
		return endState;
	}
}
