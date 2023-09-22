package it.eng.pilot;

import java.util.Date;

/**
 * Classe che implementa una iterazione per anno-mese a partire dalle date
 * inizio e fine. L'iterazione parte dall'anno e dal mese della data inizio e
 * termina con l'anno e il mese della data fine d2. La logica applicativa va
 * specificata all'interno del metodo execute che deve essere implementato dalla
 * classe che la estende.
 * 
 * @author Antonio Corinaldi
 * 
 */
public abstract class YearMonthIteration extends PilotSupport {

	private Date inizio;
	private Date fine;
	/**
	 * Indica la condizione di uscita dalla iterazione anno-mese. Si verifica al
	 * termine di ogni iterazione su mese, se true esce fuori da tutte le
	 * iterazioni e non prosegue più
	 */
	private boolean out;

	/**
	 * Esegue l'iterazione per anno-mese scandendo tutti i mesi di tutti gli
	 * anni a partire dalla data inizio fino alla data fine. All'interno di ogni
	 * iterazione esegue la logica applicativa specificata all'interno del
	 * metodo execute
	 */
	public void iterate() {
		if (tutte(notNull(inizio, fine), p.validaDate(inizio, fine, false))) {
			Integer annoStart = getYear(inizio);
			Integer annoEnd = getYear(fine);
			Integer meseStart = getMonth(inizio);
			Integer meseEnd = getMonth(fine);
			int inizio = 1;
			int fine = 12;
			anni: for (int anno = annoStart; anno <= annoEnd; anno++) {
				if (is(anno, annoStart)) {
					inizio = meseStart;
				} else {
					inizio = 1;
				}
				if (is(anno, annoEnd)) {
					fine = meseEnd;
				}
				for (int mese = inizio; mese <= fine; mese++) {
					execute(anno, mese);
					if (isOut())
						break anni;
				}
			}
		}

	}

	/**
	 * Metodo che dovrà essere implementato con la logica applicativa specifica
	 * per l'iterazione anno-mese
	 * 
	 * @param anno
	 * @param mese
	 */
	protected abstract void execute(int anno, int mese);

	public Date getInizio() {
		return inizio;
	}

	public void setInizio(Date inizio) {
		this.inizio = inizio;
	}

	public Date getFine() {
		return fine;
	}

	public void setFine(Date fine) {
		this.fine = fine;
	}

	public boolean isOut() {
		return out;
	}

	public void setOut(boolean out) {
		this.out = out;
	}

}
