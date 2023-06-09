package it.academy.gaming.milionario.core.application.views;

import it.academy.gaming.milionario.core.domain.LetteraRisposta;
import it.academy.gaming.milionario.core.domain.PercentualeRisposta;

public class PercentualeRispostaView {

	private LetteraRisposta letteraRisposta;
	private int percentuale;

	public PercentualeRispostaView(PercentualeRisposta percentualeRisposta) {
		this.letteraRisposta = percentualeRisposta.getLetteraRisposta();
		this.percentuale = percentualeRisposta.getPercentuale();
	}

	public LetteraRisposta getLetteraRisposta() {
		return letteraRisposta;
	}

	public int getPercentuale() {
		return percentuale;
	}

	@Override
	public String toString() {
		return letteraRisposta.toString() + percentuale + "%";
	}

}