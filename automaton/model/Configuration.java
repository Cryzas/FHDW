package model;

import java.util.List;

import javafx.util.Pair;

public class Configuration {

	private Automaton aut;
	private State state;
	private String input;
	private String output;
	private Manager manager;
	private Thread thread;

	/**
	 * Erstellt eine neue Konfiguration <Configuration> fuer den Automaten mit
	 * folgenden Parametern: <br>
	 * <aut>: Der Automat, für welchen die Konfiguration erstellt wird <br>
	 * <state>: Der Zustand, in welchem sich der Automat befindet <br>
	 * <input>: Der aktuelle Inputstring <br>
	 * <output>: Der aktuelle Outputstring <br>
	 * <manager>: Der Manager, welcher für den Automaten zustaendig ist. <br>
	 **/
	public static Configuration create(Automaton aut, State state, String input, String output, Manager manager) {
		return new Configuration(aut, state, input, output, manager);
	}

	/**
	 * Initialisiert die Konfiguration <Configuration> mit den Parametern.
	 **/
	private Configuration(Automaton aut, State state, String input, String output, Manager manager) {
		this.aut = aut;
		this.state = state;
		this.input = input;
		this.output = output;
		this.manager = manager;
	}

	/**
	 * Methode, welche auf Basis der Grundkonfiguration den naechsten Schritt
	 * ausführt
	 * 
	 **/
	public void step() {
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				boolean newConfExists = true; // Parameter ermöglicht somit
												// Ausführung der while-Schleife
				while (input.length() > 0 && manager.getRunning() && newConfExists) {
					// Hier findet die eigentliche Berechnung des Automaten
					// <aut> statt
					char currentChar = input.charAt(0);
					List<Pair<State, Character>> newStates = state.get(currentChar);

					if (newStates.size() > 0) {
						// Eine Rekursion, welche neue Konfiguration(en)
						// erstellt, solange es noch Folgezustände gibt
						for (int i = 1; i < newStates.size(); i++) {
							Pair<State, Character> pair = newStates.get(i);
							Configuration
									.create(aut, pair.getKey(), input.substring(1, input.length()),
											Configuration.this.output + pair.getValue(), Configuration.this.manager)
									.step(); // Eine neue Konfiguration wird
												// erstellt und mit step()
												// erneut ausgeführt
						}
						Pair<State, Character> first = newStates.get(0);
						state = first.getKey();
						input = input.substring(1, input.length());
						output = output + first.getValue();
					} else {
						newConfExists = false;
					}
				}

				if (input.length() == 0 && isEndConfiguration()) {
					manager.finishedSuccessfull(Thread.currentThread(), output);
				} else {
					manager.finishedWithFailure(Thread.currentThread());
				}
			}
		});
		manager.addThread(thread);
		thread.start();
	}

	/**
	 * Getter, welcher das Abrufen der Konfiguration des Endzustandes
	 * ermoeglicht
	 **/
	public boolean isEndConfiguration() {
		return this.state.equals(aut.getEnd());
	}

	/** Getter, welcher das Abrufen des Outputstring <output> ermoeglicht **/
	public String getOutput() {
		return this.output;
	}
}
