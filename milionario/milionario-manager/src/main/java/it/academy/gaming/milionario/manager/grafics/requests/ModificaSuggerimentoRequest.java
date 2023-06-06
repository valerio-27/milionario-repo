package it.academy.gaming.milionario.manager.grafics.requests;

import it.academy.gaming.milionario.core.domain.Accuratezza;

public class ModificaSuggerimentoRequest {
	private String testoSuggerimento;
	private Accuratezza accuratezza;
	private int tempoMinimo;

	public ModificaSuggerimentoRequest(String testoSuggerimento, Accuratezza accuratezza, int tempoMinimo) {
		super();
		this.testoSuggerimento = testoSuggerimento;
		this.accuratezza = accuratezza;
		this.tempoMinimo = tempoMinimo;
	}

	public String getTestoSuggerimento() {
		return testoSuggerimento;
	}

	public Accuratezza getAccuratezza() {
		return accuratezza;
	}

	public int getTempoMinimo() {
		return tempoMinimo;
	}

}
