package it.eng.pilot;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe di utilità per le date
 * 
 * @author Antonio Corinaldi
 * 
 */
public class PDate extends Date {

	private static final long serialVersionUID = 4730523963038718870L;
	private final Pilot p = new Pilot();

	public PDate(Date d) {
		if (p.notNull(d))
			setTime(d.getTime());
		else if (p.notNull(p.getLog()))
			p.getLog().info("Data non valida! Ritorno la data odierna.");
	}

	public PDate() {
	}

	/**
	 * Ritorna l'anno
	 * 
	 * @return Integer
	 */

	public Integer getAnno() {
		return p.getYear((this));
	}

	/**
	 * Ritorna il mese dell'anno in formato numerico
	 * 
	 * @return Integer
	 */
	public Integer getMese() {
		return p.getMonth(this);
	}

	/**
	 * Ritorna il giorno del mese
	 * 
	 * @return Integer
	 */

	public Integer getGiorno() {
		return p.getDay((this));
	}

	/**
	 * Ritorna l'ora H24
	 * 
	 * @return Integer
	 */

	public Integer getOra() {
		return p.getHour((this));
	}

	/**
	 * Ritorna i minuti dell'ora
	 * 
	 * @return Integer
	 */

	public Integer getMinuti() {
		return p.getMinute((this));
	}

	/**
	 * Ritorna i secondi del minuto
	 * 
	 * @return Integer
	 */

	public Integer getSecondi() {
		return p.getSecond((this));
	}

	/**
	 * Ritorna l'ora completa in formato hh:mm:ss
	 * 
	 * @return String
	 */
	public String getOraCompleta() {
		return p.strSep(":", getOra(), getMinuti(), getSecondi());
	}

	/**
	 * Ritorna la rappresentazione stringa della data nel formato dd/MM/yyyy
	 * 
	 * @return String
	 */

	public String toString() {
		return p.dateToString(this);
	}

	/**
	 * Ritorna la rappresentazione stringa della data nel formato dd/MM/yyyy
	 * hh:mm:ss
	 * 
	 * @return String
	 */

	public String toStringHHmmss() {
		return p.dateToStringhhmmss(this);
	}

	/**
	 * Ritorna la rappresentazione stringa della data nel formato indicato es.
	 * dd/MM/yyyy
	 * 
	 * @return String
	 */

	public String toStringFormat(String format) {
		return p.dateToString(this, format);
	}

	/**
	 * Ritorna l'oggetto Calendar corrispondente
	 * 
	 * @return Calendar
	 */

	public Calendar toCalendar() {
		return p.dateToCalendar(this);
	}

	/**
	 * Ritorna true se la data è anteriore alla data limite impostata
	 * 
	 * @param limite
	 * @return boolean
	 */

	public boolean isBefore(Date limite) {
		return p.isDateBefore(this, limite);
	}

	/**
	 * Ritorna true se la data è anteriore alla data limite impostata in formato
	 * italiano dd/MM/yyyy dd/MM/yyyy hh:mm:ss
	 * 
	 * @param limite
	 * @return boolean
	 */

	public boolean isBefore(String limite) {
		return p.isDateBefore(this, p.toDate(limite));
	}

	/**
	 * Ritorna true se la data è posteriore alla data limite impostata
	 * 
	 * @param limite
	 * @return boolean
	 */

	public boolean isAfter(Date limite) {
		return p.isDateAfter(this, limite);
	}

	/**
	 * Ritorna true se la data è posteriore alla data limite impostata in
	 * formato italiano dd/MM/yyyy dd/MM/yyyy hh:mm:ss
	 * 
	 * @param limite
	 * @return boolean
	 */

	public boolean isAfter(String limite) {
		return p.isDateAfter(this, p.toDate(limite));
	}

	/**
	 * Ritorna true se la data è non posteriore alla data limite impostata
	 * 
	 * @param limite
	 * @return boolean
	 */

	public boolean isNotAfter(Date limite) {
		return !p.isDateAfter(this, limite);
	}

	/**
	 * Ritorna true se la data è non posteriore alla data limite impostata in
	 * formato italiano dd/MM/yyyy dd/MM/yyyy hh:mm:ss
	 * 
	 * @param limite
	 * @return boolean
	 */

	public boolean isNotAfter(String limite) {
		return !p.isDateAfter(this, p.toDate(limite));
	}

	/**
	 * Ritorna true se la data è non anteriore alla data limite impostata
	 * 
	 * @param limite
	 * @return boolean
	 */
	public boolean isNotBefore(Date limite) {
		return !p.isDateBefore(this, limite);
	}

	/**
	 * Ritorna true se la data è non anteriore alla data limite impostata in
	 * formato italiano dd/MM/yyyy dd/MM/yyyy hh:mm:ss
	 * 
	 * @param limite
	 * @return boolean
	 */
	public boolean isNotBefore(String limite) {
		return !p.isDateBefore(this, p.toDate(limite));
	}

	/**
	 * Ritorna true se la data è compresa nell'intervallo [start,end] estremi
	 * inclusi
	 * 
	 * @param start
	 * @param end
	 * @return boolean
	 */
	public boolean isBetween(Date start, Date end) {
		return p.isDateBetween(this, start, end);
	}

	/**
	 * Ritorna true se la data è compresa nell'intervallo [start,end] estremi
	 * inclusi espressi in formato italiano dd/MM/yyyy dd/MM/yyyy hh:mm:ss
	 * 
	 * @param start
	 * @param end
	 * @return boolean
	 */
	public boolean isBetween(String start, String end) {
		return p.isDateBetween(this, p.toDate(start), p.toDate(end));
	}

	/**
	 * Ritorna true se la data è compresa nell'intervallo ]start,end[ estremi
	 * esclusi
	 * 
	 * @param start
	 * @param end
	 * @return boolean
	 */
	public boolean isBetweenEx(Date start, Date end) {
		return p.isDateBetweenEx(this, start, end);
	}

	/**
	 * Ritorna true se la data è compresa nell'intervallo ]start,end[ estremi
	 * esclusi espressi in formato italiano dd/MM/yyyy dd/MM/yyyy hh:mm:ss
	 * 
	 * @param start
	 * @param end
	 * @return boolean
	 */
	public boolean isBetweenEx(String start, String end) {
		return p.isDateBetweenEx(this, p.toDate(start), p.toDate(end));
	}

	/**
	 * Ritorna un nuovo oggetto PDate maggiorato del numero di giorni indicato
	 * 
	 * @param val
	 * @return PDate
	 */
	public PDate addDays(Integer val) {
		return p.pd(p.addDays(this, val));
	}

	/**
	 * Ritorna un nuovo oggetto PDate maggiorato del numero di ore indicato
	 * 
	 * @param val
	 * @return PDate
	 */
	public PDate addHours(Integer val) {
		return p.pd(p.addHours(this, val));
	}

	/**
	 * Ritorna un nuovo oggetto PDate maggiorato del numero di minuti indicato
	 * 
	 * @param val
	 * @return PDate
	 */
	public PDate addMinutes(Integer val) {
		return p.pd(p.addMinutes(this, val));
	}

	/**
	 * Ritorna un nuovo oggetto PDate maggiorato del numero di secondi indicato
	 * 
	 * @param val
	 * @return PDate
	 */
	public PDate addSeconds(Integer val) {
		return p.pd(p.addSeconds(this, val));
	}

	/**
	 * Ritorna un nuovo oggetto PDate maggiorato del numero di millisecondi
	 * indicato
	 * 
	 * @param val
	 * @return PDate
	 */
	public PDate addMilliseconds(Integer val) {
		return p.pd(p.addMilliseconds(this, val));
	}

	/**
	 * Ritorna un nuovo oggetto PDate maggiorato del numero di mesi indicato
	 * 
	 * @param val
	 * @return PDate
	 */
	public PDate addMonths(Integer val) {
		return p.pd(p.addMonths(this, val));
	}

	/**
	 * Ritorna un nuovo oggetto PDate maggiorato del numero di anni indicato
	 * 
	 * @param val
	 * @return PDate
	 */
	public PDate addYears(Integer val) {
		return p.pd(p.addYears(this, val));
	}

	/**
	 * Ritorna la rappresentazione stringa letterale del mese numerico della
	 * data
	 * 
	 * @return String
	 */
	public String getMeseLiteral() {
		return p.decodeMonth(String.valueOf(getMese()));
	}

	/**
	 * Data una data torna una stringa in formato dd/mm/yyyy del primo giorno
	 * del mese
	 * 
	 * @return String
	 */
	public String getPrimoGiornoDelMese() {
		return p.primoGiornoDelMese(this);
	}

	/**
	 * Data una data torna una stringa in formato dd/mm/yyyy del primo giorno
	 * del mese successivo
	 * 
	 * @return String
	 */
	public String getPrimoGiornoDelMeseSuccessivo() {
		return p.primoGiornoDelMeseSuccessivo(this);
	}

	/**
	 * Imposta la data al primo giorno del mese
	 * 
	 * @return PDate
	 */
	public PDate impostaAlPrimoGiornoDelMese() {
		return p.pd(p.primoGiornoDelMeseDate(this));
	}

	/**
	 * Ritorna l'oggetto Timestamp della data
	 * 
	 * @return Timestamp
	 */
	public Timestamp getTS() {
		return p.getTimestamp(this);
	}

	/**
	 * Ritorna l'oggetto SQLDate
	 * 
	 * @return SQLDate
	 */
	public java.sql.Date toSQLDate() {
		return p.getSQLDate(this);
	}

	/**
	 * Ritorna il numero di mesi tra la data e la data impostata come parametro
	 * 
	 * @param endDate
	 * @return int
	 */
	public int monthsToDate(Date endDate) {
		return p.monthsBetweenDates(this, endDate);
	}

	/**
	 * Ritorna il numero di mesi tra la data e la data impostata come parametro
	 * in formato italiano dd/MM/yyyy dd/MM/yyyy hh:mm:sss
	 * 
	 * @param endDate
	 * @return int
	 */
	public int monthsToDate(String endDate) {
		return p.monthsBetweenDates(this, p.toDate(endDate));
	}

	/**
	 * Ritorna il numero di anni tra la data e la data impostata come parametro
	 * 
	 * @param endDate
	 * @return int
	 */
	public int yearsToDate(Date endDate) {
		return p.yearsBetweenDates(this, endDate);
	}

	/**
	 * Ritorna il numero di anni tra la data e la data impostata come parametro
	 * in formato italiano dd/MM/yyyy dd/MM/yyyy hh:mm:ss
	 * 
	 * @param endDate
	 * @return int
	 */
	public int yearsToDate(String endDate) {
		return p.yearsBetweenDates(this, p.toDate(endDate));
	}

	/**
	 * Ritorna una stringa che indica il tempo trascorso tra la data e la data
	 * fine passata come parametro
	 * 
	 * @param endDate
	 * @return String
	 */
	public String elapsedTime(Date endDate) {
		return p.elapsedTime(this, endDate);
	}

	/**
	 * Ritorna una stringa che indica il tempo trascorso tra la data e la data
	 * fine passata come parametro in formato italiano dd/MM/yyyy dd/MM/yyyy
	 * hh:mm:ss
	 * 
	 * @param endDate
	 * @return String
	 */
	public String elapsedTime(String endDate) {
		return p.elapsedTime(this, p.toDate(endDate));
	}

	/**
	 * Torna true se la data coincide con l'ultimo giorno del mese
	 * 
	 * @return boolean
	 */
	public boolean isLastDayOfMonth() {
		return p.isLastDayOfMonth(this);
	}

	/**
	 * Data una stringa che rappresenta una data, ritorna l'oggetto Date
	 * corrispondente. Formati dd/mm/yyyy o dd/mm/yyyy hh:mm:ss
	 * 
	 * @param s
	 * @return PDate
	 */
	public PDate from(String s) {
		return p.pd(p.toDate(s));

	}
}
