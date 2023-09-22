package it.eng.pilot;

import java.util.Date;

/**
 * Classe che consente di incapsulare un contatore di chiamata e un contatore
 * temporale. Utile per rilevare il numero di chiamate a un servizio, il tempo
 * totale impiegato e il tempo medio impiegato a rispondere. Utilizzata per
 * rilevare i tempi intermedi durante una elaborazione.
 * 
 * @author Antonio Corinaldi
 * 
 */
public class TimeCount extends PilotSupport {

	private Integer count = 0;

	private Time time = new Time();

	private String titolo = "";

	private Time lastAddedTime = new Time();

	public TimeCount(String titolo) {
		setTitolo(titolo);
	}

	/**
	 * Ritorna il numero di chiamate
	 * 
	 * @return Integer
	 */
	public Integer getCount() {
		return count;
	}

	private void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * Ritorna l'oggetto time complessivo
	 * 
	 * @return Time
	 */
	public Time getTime() {
		return time;
	}

	private void addCount() {
		setCount(getCount() + 1);
	}

	/**
	 * Incrementa il tempo della quantità di tempo indicata
	 * 
	 * @param time
	 */
	public void addTime(Time time) {
		addCount();
		getTime().addTime(time);
		setLastAddedTime(time);
	}

	/**
	 * Ritorna le info come stringa, indicando il titolo, il numero delle
	 * chiamate, il tempo totale e il tempo medio impiegato
	 * 
	 * @return String
	 */
	public String getInfo() {
		String info = "";
		if (getCount() > 0) {
			info = str(getTitolo(), ":[", getCount(), " chiamate. Tempo totale=", getTotalTime(), ". Tempo medio=", getMeanTime(), "]");
		}
		return info;
	}

	/**
	 * Incrementa il tempo della quantità di tempo trascorsa a partire da
	 * startDate fino a now e incrementa di uno il contatore di chiamate
	 * 
	 * @param startDate
	 */
	public void addTime(Date startDate) {
		addCount();
		Time t = elapsed(startDate);
		getTime().addTime(t);
		setLastAddedTime(t);
	}

	/**
	 * Ritorna la rappresentazione stringa del tempo medio
	 * 
	 * @return String
	 */
	public String getMeanTime() {
		if (zero(getCount()))
			return "[0 seconds]";
		else
			return str("[", elapsedTime(getTime().getDuration() / getCount()), "]");
	}

	/**
	 * Ritorna la rappresentazione stringa del tempo totale
	 * 
	 * @return String
	 */
	public String getTotalTime() {
		return getTime().getTotalTime();
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	/**
	 * Ritorna l'oggetto time aggiunto attraverso addTime
	 * 
	 * @return Time
	 */
	public Time getLastAddedTime() {
		return lastAddedTime;
	}

	private void setLastAddedTime(Time lastAddedTime) {
		this.lastAddedTime = lastAddedTime;
	}

}
