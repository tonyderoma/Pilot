package it.eng.pilot;

import java.io.Serializable;

/**
 * Enumerazione degli operatori booleani di concatenazione delle condizioni di
 * where.
 * 
 * @author Antonio Corinaldi
 * 
 */
public enum OperatoreBooleano implements Serializable {

	AND(" AND "), OR(" OR ");
	String op = null;

	OperatoreBooleano(String s) {
		setOp(s);
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

}
