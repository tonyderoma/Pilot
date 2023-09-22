package it.eng.pilot;

import java.io.Serializable;

/**
 * Classe di implementazione di una struttura switch case. "Condizione" è la
 * condizione boolean che rende valida la mappatura specifica se true. "Valore"
 * è il valore originale. "Diventa" è come viene tradotto quel o quei valori
 * originali (in questo caso si usa il costruttore con più valori originali).
 * 
 * @author Antonio Corinaldi
 */
public class CaseCondition implements Serializable {

	private static final String COMMA = Pilot.COMMA;
	private static final String PIPE = Pilot.PIPE;
	private static final long serialVersionUID = 7014413558130106917L;
	private boolean condizione = true;
	private String valore;
	private String diventa;

	public CaseCondition(boolean condizione, String valore) {
		setValore(valore);
		setCondizione(condizione);
	}

	public CaseCondition(boolean condizione, String diventa, String... valoriOriginali) {
		setCondizione(condizione);
		StringBuffer sb = new StringBuffer();
		for (String val : valoriOriginali) {
			sb.append(val).append(COMMA);
		}
		if (sb.length() > 1) {
			sb.setLength(sb.length() - 1);
		}
		setValore(sb.toString() + PIPE + diventa);
	}

	public CaseCondition(String diventa, String... valoriOriginali) {
		StringBuffer sb = new StringBuffer();
		for (String val : valoriOriginali) {
			sb.append(val).append(COMMA);
		}
		if (sb.length() > 1) {
			sb.setLength(sb.length() - 1);
		}
		setValore(sb.toString() + PIPE + diventa);
	}

	public CaseCondition(String valore) {
		setValore(valore);
	}

	public boolean isCondizione() {
		return condizione;
	}

	public void setCondizione(boolean condizione) {
		this.condizione = condizione;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

	public String getDiventa() {
		return diventa;
	}

	public void setDiventa(String diventa) {
		this.diventa = diventa;
	}

}
