package it.eng.pilot;

import java.io.Serializable;
import java.util.List;

/**
 * Classe che definisce un generico filtro per l'oggetto PArrayList.
 * 
 * @author Antonio Corinaldi
 */
class Filter<T> implements Serializable {

	private static final long serialVersionUID = -8291061081160687401L;
	private String campo;
	private Object[] vals = null;
	private Operatore op;
	private final Pilot p = new Pilot();

	Filter(String campo, Operatore op) {
		setCampo(campo);
		setOp(op);
	}

	Filter(String campo, Operatore op, T v1) {
		this(campo, op);
		vals = new Object[1];
		vals[0] = v1;
	}

	Filter(String campo, Operatore op, T v1, T v2) {
		this(campo, op);
		vals = new Object[2];
		vals[0] = v1;
		vals[1] = v2;
	}

	Filter(String campo, Operatore op, List<T> valori) {
		this(campo, op);
		valori = p.safe(valori);
		vals = new Object[valori.size()];
		int i = 0;
		for (T item : valori) {
			vals[i] = item;
			i++;
		}
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public Operatore getOp() {
		return op;
	}

	public void setOp(Operatore op) {
		this.op = op;
	}

	public Object[] getVals() {
		return vals;
	}

}
