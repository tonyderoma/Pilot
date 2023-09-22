package it.eng.pilot;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe utilizzata per accedere al metodo selectCascade di DaoHelper.
 * 
 * 
 * @author Antonio Corinaldi
 * 
 */
public class EntityWired implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1846445218124786503L;
	/**
	 * entAlias è l'alias dell'entity @Table[alias=""]
	 */
	private String entAlias;
	/**
	 * Stato è un insieme di valori che saranno filtrati sulla corrispondente
	 * colonna che ha @Column[stato=true]
	 */
	private Object[] stato;

	/**
	 * betweenSysdate se true, intende filtrare per sysdate appartenente
	 * all'intervallo [dataInizio,dataFine] dove dataInizio è la colonna
	 * dell'Entity con @Column[startDate=true] e dataFine è la colonna
	 * dell'Entity con @Column[endDate=true]
	 */

	private boolean betweenSysdate;
	/**
	 * Stato1 è un insieme di valori che saranno filtrati sulla corrispondente
	 * colonna che ha @Column[stato1=true]
	 */
	private Object[] stato1;
	/**
	 * dateBefore se true esegue un filtro per campoDataInizio<=date dove
	 * campoDataInizio è la colonna dell'Entity con @Column[startDate=true]
	 */
	private boolean dateBefore;
	/**
	 * Valore della data usato nelle relazioni per dateBefore e dateAfter
	 */
	private Date date;
	/**
	 * dateAfter se true esegue un filtro per campoDataInizio<=date dove
	 * campoDataInizio è la colonna dell'Entity con @Column[startDate=true]
	 */
	private boolean dateAfter;

	private boolean goPk;

	public EntityWired(String entAlias) {
		setEntAlias(entAlias);
	}

	public EntityWired(String entAlias, Object... stato) {
		this(entAlias);
		setStato(stato);
	}

	public Object[] getStato() {
		return stato;
	}

	public EntityWired setStato(Object[] stato) {
		this.stato = stato;
		return this;
	}

	public boolean isBetweenSysdate() {
		return betweenSysdate;
	}

	public EntityWired setBetweenSysdate(boolean betweenSysdate) {
		this.betweenSysdate = betweenSysdate;
		return this;
	}

	public Object[] getStato1() {
		return stato1;
	}

	public EntityWired setStato1(Object... stato1) {
		this.stato1 = stato1;
		return this;
	}

	public boolean isDateBefore() {
		return dateBefore;
	}

	public EntityWired setDateBefore(boolean dateBefore) {
		this.dateBefore = dateBefore;
		return this;
	}

	public Date getDate() {
		return date;
	}

	public EntityWired setDate(Date d) {
		this.date = d;
		return this;
	}

	public boolean isDateAfter() {
		return dateAfter;
	}

	public EntityWired setDateAfter(boolean dateAfter) {
		this.dateAfter = dateAfter;
		return this;
	}

	public String getEntAlias() {
		return entAlias;
	}

	public EntityWired setEntAlias(String entAlias) {
		this.entAlias = entAlias;
		return this;
	}

	public boolean isGoPk() {
		return goPk;
	}

	public EntityWired setGoPk(boolean goPk) {
		this.goPk = goPk;
		return this;
	}

}
