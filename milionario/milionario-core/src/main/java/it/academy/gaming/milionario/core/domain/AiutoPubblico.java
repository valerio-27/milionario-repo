package it.academy.gaming.milionario.core.domain;

import it.academy.gaming.milionario.core.domain.exceptions.AiutoNonDisponibileException;

public class AiutoPubblico {

	private RangeCulturaGenerale range;
	private PercentualeFortuna percentualeFortuna;
	private boolean disponibile = true;

	public AiutoPubblico(RangeCulturaGenerale range, PercentualeFortuna percentualeFortuna) {
		this.range = range;
		this.percentualeFortuna = percentualeFortuna;
	}

	public Votazione vota(Quesito quesito) throws AiutoNonDisponibileException {

		if(!disponibile) {
			throw AiutoNonDisponibileException.aiutoPubblico();
		}
		disponibile = false;
		return Pubblico.vota(quesito,range,percentualeFortuna);
	}

}
