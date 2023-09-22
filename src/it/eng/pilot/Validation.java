package it.eng.pilot;

import java.io.Serializable;

/**
 * Classe che modella una validazione generica. Ha come attributi un booleano
 * che rappresenta la validazione sul singolo campo tramite uno dei metodi
 * valida e il relativo messaggio di errore nel caso in cui la condizione di
 * validità non risulti soddisfatta.
 * 
 * @author Antonio Corinaldi
 */
public class Validation implements Serializable {

	private static final long serialVersionUID = 3630318299396268307L;
	private boolean valido;
	private String messaggio;
	private boolean applicareLaVerifica = true;

	private boolean isApplicareLaVerifica() {
		return applicareLaVerifica;
	}

	private void setApplicareLaVerifica(boolean applicareLaVerifica) {
		this.applicareLaVerifica = applicareLaVerifica;
	}

	public Validation(Boolean valido, String messaggio) {
		setValido(valido);
		setMessaggio(messaggio);
	}

	public Validation(Boolean applicareLaVerifica, Boolean valido, String messaggio) {
		this(valido, messaggio);
		setApplicareLaVerifica(applicareLaVerifica);
	}

	private boolean isValido() {
		return valido;
	}

	private void setValido(boolean valido) {
		this.valido = valido;
	}

	public String getMessaggio() {
		return messaggio;
	}

	private void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	public String execVal() {
		if (isApplicareLaVerifica() && !isValido())
			return getMessaggio();
		return null;
	}
}
