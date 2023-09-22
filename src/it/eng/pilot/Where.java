package it.eng.pilot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe per la composizione della where condition delle query.
 * 
 * @author Antonio Corinaldi
 * 
 */
public class Where implements Serializable {

	private static final long serialVersionUID = -943332024064505988L;
	private boolean par;
	private boolean closePar;
	String campo;
	String prefixColonna = "";
	String suffixColonna = "";
	Operatore op;
	Object v1;
	Object v2;
	String valore;
	OperatoreBooleano bop;
	boolean annnullatov1;
	boolean annnullatov2;
	List<Object> vals = new ArrayList<Object>();
	private boolean opUnario;

	public Where() {
	}

	public Where(String campo, Operatore o, List<Object> vals, OperatoreBooleano bop) {
		setCampo(campo);
		setOp(o);
		setVals(vals);
		setBop(bop);

	}

	public Where(String campo, String prefix, String suffix, Operatore o, List<Object> vals, OperatoreBooleano bop) {
		this(campo, o, vals, bop);
		setPrefixColonna(prefix);
		setSuffixColonna(suffix);

	}

	public Where(String campo, Operatore o, Object v1, Object v2, OperatoreBooleano bop) {
		setCampo(campo);
		setOp(o);
		setV1(v1);
		setV2(v2);
		setBop(bop);

	}

	public Where(String campo, String prefix, String suffix, Operatore o, Object v1, Object v2, OperatoreBooleano bop) {
		this(campo, o, v1, v2, bop);
		setPrefixColonna(prefix);
		setSuffixColonna(suffix);
	}

	public Where(String campo, Operatore o, Object v1, OperatoreBooleano bop) {
		setCampo(campo);
		setOp(o);
		if (v1 instanceof List) {
			setVals((List) v1);
		} else {
			setV1(v1);
		}
		setBop(bop);

	}

	public Where(String campo, String prefix, String suffix, Operatore o, Object v1, OperatoreBooleano bop) {
		setPrefixColonna(prefix);
		setSuffixColonna(suffix);
		setCampo(campo);
		setOp(o);
		setV1(v1);
		setBop(bop);

	}

	public Where(String campo, String prefix, String suffix, Operatore o, OperatoreBooleano bop) {
		setPrefixColonna(prefix);
		setSuffixColonna(suffix);
		setCampo(campo);
		setOp(o);
		setBop(bop);

	}

	public Where(String campo, Operatore o, OperatoreBooleano bop) {
		setCampo(campo);
		setOp(o);
		setBop(bop);
		setOpUnario(true);
	}

	public Where(Operatore o, Integer l, OperatoreBooleano bop) {
		setOp(o);
		setV1(l);
		setBop(bop);
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

	public Operatore getOp() {
		return op;
	}

	public void setOp(Operatore op) {
		this.op = op;
	}

	public Object getV1() {
		return v1;
	}

	public void setV1(Object v1) {
		this.v1 = v1;
	}

	public Object getV2() {
		return v2;
	}

	public void setV2(Object v2) {
		this.v2 = v2;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public OperatoreBooleano getBop() {
		return bop;
	}

	public void setBop(OperatoreBooleano bop) {
		this.bop = bop;
	}

	public boolean isAnnnullatov1() {
		return annnullatov1;
	}

	public void setAnnnullatov1(boolean annnullatov1) {
		this.annnullatov1 = annnullatov1;
	}

	public boolean isAnnnullatov2() {
		return annnullatov2;
	}

	public void setAnnnullatov2(boolean annnullatov2) {
		this.annnullatov2 = annnullatov2;
	}

	public List<Object> getVals() {
		return vals;
	}

	public void setVals(List<Object> vals) {
		this.vals = vals;
	}

	public String getPrefixColonna() {
		return prefixColonna;
	}

	public void setPrefixColonna(String prefixColonna) {
		this.prefixColonna = prefixColonna;
	}

	public String getSuffixColonna() {
		return suffixColonna;
	}

	public void setSuffixColonna(String suffixColonna) {
		this.suffixColonna = suffixColonna;
	}

	public boolean isOpUnario() {
		return opUnario;
	}

	public void setOpUnario(boolean opUnario) {
		this.opUnario = opUnario;
	}

	public boolean isPar() {
		return par;
	}

	public void setPar(boolean par) {
		this.par = par;
	}

	public boolean isClosePar() {
		return closePar;
	}

	public void setClosePar(boolean closePar) {
		this.closePar = closePar;
	}

}
