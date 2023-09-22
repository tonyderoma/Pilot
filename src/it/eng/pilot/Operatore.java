package it.eng.pilot;

import java.io.Serializable;

/**
 * Enumerazione per gli operatori relazionali utili a definire le where
 * condition nelle query e nelle ricerche (find) su liste.
 * 
 * @author Antonio Corinaldi
 * 
 */
public enum Operatore implements Serializable {

	BETWEEN(" BETWEEN "), EQUAL(" = "), NOT_EQUAL(" <> "), GTE(" >= "), GT(" > "), LTE(" <= "), LT(" < "), ISNULL(" IS NULL "), ISNOTNULL(" IS NOT NULL "), IN(" IN ( ", ")"), NOTIN(" NOT IN ( ", ")"), LIKE(
			" LIKE "), NOTLIKE(" NOT LIKE "), LIMIT(" ROWNUM <= ");
	String op = null;
	String suffixOp = "";

	Operatore(String s) {
		setOp(s);
	}

	Operatore(String s, String suffix) {
		setOp(s);
		setSuffixOp(suffix);
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getSuffixOp() {
		return suffixOp;
	}

	public void setSuffixOp(String suffixOp) {
		this.suffixOp = suffixOp;
	}

}
