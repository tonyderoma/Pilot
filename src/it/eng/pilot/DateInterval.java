package it.eng.pilot;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe che mappa un range di date [inizio,fine].
 * 
 * @author Antonio Corinaldi
 * 
 */
public class DateInterval implements Serializable {

	private static final long serialVersionUID = -1764265690303267073L;
	private Date start;
	private Date end;
	private final Pilot p = new Pilot();

	public DateInterval(String start1, String end1) throws Exception {
		if (!p.validaDate(start1, end1, false))
			throw new Exception("Intervallo di date non valido,[" + start1 + "," + end1 + "]");

		setStart(p.toDate(start1));
		setEnd(p.toDate(end1));

	}

	public DateInterval(Date start1, Date end1) throws Exception {
		if (!p.validaDate(start1, end1, false))
			throw new Exception("Intervallo di date non valido,[" + start1 + "," + end1 + "]");

		setStart(start1);
		setEnd(end1);

	}

	public Date getStart() {
		return start;
	}

	private void setStart(Date start1) {
		this.start = start1;
	}

	public Date getEnd() {
		return end;
	}

	private void setEnd(Date end1) {
		this.end = end1;
	}

}
