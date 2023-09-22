package it.eng.pilot;

import java.io.Serializable;

/**
 * Classe che implementa una durata temporale in
 * giorni/ore/minuti/secondi/millisecodi.
 * 
 * @author Antonio Corinaldi
 * 
 */
public class Time implements Serializable {

	private static final long serialVersionUID = 6291770500473227832L;
	public static final String DAYS = DaoHelper.DAYS;
	public static final String HOURS = DaoHelper.HOURS;
	public static final String MINUTES = DaoHelper.MINUTES;
	public static final String SECONDS = DaoHelper.SECONDS;
	public static final String MILLISECONDS = DaoHelper.MILLISECONDS;
	private Long days = 0l;
	private Long hours = 0l;
	private Long minutes = 0l;
	private Long seconds = 0l;
	private Long milliseconds = 0l;
	private String livelloGiorni = Pilot.livelloGiorni;
	private String livelloOre = Pilot.livelloOre;
	private String livelloMinuti = Pilot.livelloMinuti;
	private String livelloSecondi = Pilot.livelloSecondi;
	private String livelloMilliSecondi = Pilot.livelloMilliSecondi;

	public Long getDays() {
		return days;
	}

	public void setDays(Long days) {
		this.days = days;
	}

	public Long getHours() {
		return hours;
	}

	public void setHours(Long hours) {
		this.hours = hours;
	}

	public Long getMinutes() {
		return minutes;
	}

	public void setMinutes(Long minutes) {
		this.minutes = minutes;
	}

	public Long getSeconds() {
		return seconds;
	}

	public void setSeconds(Long seconds) {
		this.seconds = seconds;
	}

	public Long getMilliseconds() {
		return milliseconds;
	}

	public void setMilliseconds(Long milliseconds) {
		this.milliseconds = milliseconds;
	}

	public Time addDays(Long val) {
		setDays(getDays() + val);
		return this;
	}

	public Time addMinutes(Long val) {
		setMinutes(getMinutes() + val);
		return this;
	}

	public Time addHours(Long val) {
		setHours(getHours() + val);
		return this;
	}

	public Time addSeconds(Long val) {
		setSeconds(getSeconds() + val);
		return this;
	}

	public Time addMilliseconds(Long val) {
		setMilliseconds(getMilliseconds() + val);
		return this;
	}

	private void computeTime() {
		if (getMilliseconds() > 0) {
			long elapsedSeconds = getMilliseconds() / 1000l;
			addSeconds(elapsedSeconds);
			setMilliseconds(getMilliseconds() - (elapsedSeconds * 1000l));
		}
		if (getSeconds() > 0) {
			long elapsedMinutes = getSeconds() / 60l;
			addMinutes(elapsedMinutes);
			setSeconds(getSeconds() - (elapsedMinutes * 60l));
		}
		if (getMinutes() > 0) {
			long elapsedHours = getMinutes() / 60l;
			addHours(elapsedHours);
			setMinutes(getMinutes() - (elapsedHours * 60l));
		}
		if (getHours() > 0) {
			long elapsedDays = getHours() / 24l;
			addDays(elapsedDays);
			setHours(getHours() - (elapsedDays * 24l));
		}

	}

	/**
	 * Ritorna il numero di millisecondi corrispondenti all'oggetto Time
	 * 
	 * @return Long
	 */
	public Long getDuration() {
		Long ris = 0l;
		long millisInSecond = 1000;
		long millisInMinute = 60 * millisInSecond;
		long millisInHour = 60 * millisInMinute;
		long millisInDay = 24 * millisInHour;
		ris += getDays() * millisInDay;
		ris += getHours() * millisInHour;
		ris += getMinutes() * millisInMinute;
		ris += getSeconds() * millisInSecond;
		ris += getMilliseconds();
		return ris;
	}

	/**
	 * Ritorna la rappresentazione stringa dell'oggetto in formato <numGiorni>
	 * days, <numOre> hours, <numMinuti> minutes, <numSeconds> seconds,
	 * <numMilliseconds> milliseconds
	 * 
	 * @return String
	 */
	public String getTotalTime() {
		computeTime();
		long elapsedDays = getDays();
		long elapsedHours = getHours();
		long elapsedMinutes = getMinutes();
		long elapsedSeconds = getSeconds();
		long elapsedMilliseconds = getMilliseconds();
		String time = "";
		if (elapsedDays > 0) {
			time = String.format(livelloGiorni, elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds, elapsedMilliseconds);
		} else if (elapsedHours > 0) {
			time = String.format(livelloOre, elapsedHours, elapsedMinutes, elapsedSeconds, elapsedMilliseconds);
		} else if (elapsedMinutes > 0) {
			time = String.format(livelloMinuti, elapsedMinutes, elapsedSeconds, elapsedMilliseconds);
		} else if (elapsedSeconds > 0) {
			time = String.format(livelloSecondi, elapsedSeconds, elapsedMilliseconds);
		} else {
			time = String.format(livelloMilliSecondi, elapsedMilliseconds);
		}
		return DaoHelper.OPEN_QUADRA + time + DaoHelper.CLOSE_QUADRA;
	}

	public void addTime(Time t) {
		if (null != t) {
			addDays(t.getDays());
			addHours(t.getHours());
			addMinutes(t.getMinutes());
			addSeconds(t.getSeconds());
			addMilliseconds(t.getMilliseconds());
		}

	}
}
