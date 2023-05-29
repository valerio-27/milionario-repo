package it.academy.gaming.milionario.manager.grafics.requests;

public class RecuperaQuesitoRequest {
	private String codiceQuesitoRicercato;

	public RecuperaQuesitoRequest(String codiceQuesitoRicercato) {
		super();
		this.codiceQuesitoRicercato = codiceQuesitoRicercato;
	}

	public String getCodiceQuesitoRicercato() {
		return codiceQuesitoRicercato;
	}

}
