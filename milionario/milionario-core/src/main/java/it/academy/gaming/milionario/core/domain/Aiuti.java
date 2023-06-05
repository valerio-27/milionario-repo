package it.academy.gaming.milionario.core.domain;

import it.academy.gaming.milionario.core.domain.exceptions.AiutoNonDisponibileException;

/**
 * rimane instanziato per tutto il ciclo di vita dell'applicazione
 * 
 * @author Valerio.Crispini
 *
 */
public class Aiuti {

	private AiutoCasa aiutoCasa;
	private AiutoPubblico aiutoPubblico;

	public Aiuti(RangeCulturaGenerale rangeCulturaGenerale, PercentualeFortuna percentualeFortuna) {
		this.aiutoPubblico = new AiutoPubblico(rangeCulturaGenerale, percentualeFortuna);
		this.aiutoCasa = new AiutoCasa(rangeCulturaGenerale);
		AiutoComputer.ripristina();
	}

	public void usaAiutoComputer(Quesito quesito) throws AiutoNonDisponibileException {
		AiutoComputer.vota(quesito);
	}

	public Votazione usaAiutoPubblico(Quesito quesito) throws AiutoNonDisponibileException {
		return aiutoPubblico.vota(quesito);
	}

	public Suggerimento usaAiutoCasa(Quesito quesito,Giocatore giocatore) throws AiutoNonDisponibileException {
		return aiutoCasa.vota(quesito,giocatore);
	}

	public void ripristinaAiuti() {
		AiutoComputer.ripristina();
		this.aiutoCasa.ripristina();
		this.aiutoPubblico.ripristina();
	}
}