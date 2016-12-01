package model;

import java.util.LinkedList;
import java.util.List;

/** Verwaltet die Steps der Configuation **/
public class Manager {

	private List<Thread> threads;
	private Lock waitingForFinish;
	private boolean running;
	private String output;

	public Manager() {
		this.threads = new LinkedList<Thread>();
		this.waitingForFinish = new Lock(true);
		this.running = true;
		this.output = "";
	}

	/** Fuegt zur Liste einen neuen Thread hinzu **/
	synchronized public void addThread(Thread t) {
		System.out.println("new Thread");
		this.threads.add(t);
	}

	/**
	 * Entfernt bei Aufruf den Thread <t> aus der Liste und wirft "failure" aus
	 * <br>
	 * Gibt es keine weiteren Threads, wird setRunning auf "false" gesetzt und
	 * das Lock des Managers wird geoeffnet
	 **/
	synchronized public void finishedWithFailure(Thread t) {
		System.out.println("failure");
		this.threads.remove(t);
		if (threads.isEmpty()) {
			setRunning(false);
			waitingForFinish.unlock();
		}
	}

	/**
	 * Wirft "success" aus <br>
	 * Es wird setRunning auf "false" gesetzt und das Lock des Managers wird
	 * geoeffnet
	 **/
	synchronized public void finishedSuccessfull(Thread t, String output) {
		System.out.println("success");
		setRunning(false);
		this.output = output;
		waitingForFinish.unlock();
	}

	/**
	 * Getter, welcher "true" oder "false" zurueck gibt, je nach dem ob der
	 * Manager noch laeuft
	 **/
	public boolean getRunning() {
		return this.running;
	}

	/**
	 * Setter, welcher den Parameter <running> auf "true" oder "false" setzt
	 **/
	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * Getter, welcher zunaechst den Manager locked und dann den finalen Output
	 * String ausgibt
	 **/
	public String getOutput() {
		waitingForFinish.lock();
		System.out.println("Fertig");
		return this.output;
	}
}
