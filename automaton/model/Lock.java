package model;

public class Lock {

	private boolean locked;

	/**
	 * Erstellt ein neues Lock, der Parameter <locked> setzt den Wert des Locks
	 * fest
	 **/
	public Lock(boolean locked) {
		this.locked = locked;
	}

	/**
	 * Methode zum Veraendern des Zustandes von Lock auf <locked> = true<br>
	 * Wartet, falls Lock bereits <locked> = true
	 **/
	synchronized public void lock() {
		while (this.locked) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.locked = true;
	}

	/**
	 * Methode zum Veraendern des Zustandes von Lock auf <locked> = false
	 * Benachritigt ggf. wartende Locks
	 **/
	synchronized public void unlock() {
		this.locked = false;
		this.notify();
	}
}
