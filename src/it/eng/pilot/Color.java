package it.eng.pilot;

public enum Color {
	ROSSO(91), GIALLO(93), VERDE(92), BLU(94), VIOLA(95), AZZURRO(96), GRIGIO(90), NERO(30), BIANCO_QUADRATO(7), BIANCO_BARRATO(9), BIANCO_CORNICE_VUOTO(51), GRIGIO_CORNICE(100), AZZURRO_CORNICE(
			46), VIOLA_CORNICE(45), BLU_CORNICE(44), GIALLO_CORNICE(43), VERDE_CORNICE(42), ROSSO_CORNICE(41), NERO_CORNICE(40);

	private Integer c = 0;

	private Color(Integer c) {
		this.c = c;
	}

	public Integer getC() {
		return c;
	}

	public void setC(Integer c) {
		this.c = c;
	}

}
