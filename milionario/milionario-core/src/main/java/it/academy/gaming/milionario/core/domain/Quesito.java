package it.academy.gaming.milionario.core.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import it.academy.gaming.milionario.core.domain.codici.CodiceQuesito;
import it.academy.gaming.milionario.core.domain.exceptions.CreazioneQuesitoException;
import it.academy.gaming.milionario.core.domain.exceptions.NumeroMassimoRisposteSuperatoException;
import it.academy.gaming.milionario.core.domain.exceptions.RisposteInvalideException;
import it.academy.gaming.milionario.core.domain.exceptions.SuggerimentiInvalidiException;

public class Quesito {

	private static Random random = new Random();

	private CodiceQuesito codice;
	private Domanda domanda;
	private Risposta[] risposte;
	private Difficolta difficolta;
	private Valore valore;
	private Map<Accuratezza, List<Suggerimento>> suggerimentiPerAccuratezza;

	private Quesito(Domanda domanda, Risposta[] risposte, Difficolta difficolta,
			Map<Accuratezza, List<Suggerimento>> suggerimentiPerAccuratezza) {
		this(CodiceQuesito.crea(), domanda, risposte, difficolta, suggerimentiPerAccuratezza);
	}

	private Quesito(CodiceQuesito codiceQuesito, Domanda domanda, Risposta[] risposte, Difficolta difficolta,
			Map<Accuratezza, List<Suggerimento>> suggerimentiPerAccuratezza) {
		this.codice = codiceQuesito;
		this.domanda = domanda;
		this.risposte = risposte;
		this.difficolta = difficolta;
		this.suggerimentiPerAccuratezza = suggerimentiPerAccuratezza;
		this.valore = Valore.calcola(difficolta);
	}

	public boolean indovina(LetteraRisposta lettera) {
		if (getLetteraRispostaCorretta().equals(lettera)) {
			return true;
		}
		return false;
	}

	public Suggerimento getSuggerimentoDaCasaRandom(Accuratezza accuratezza) {
		List<Suggerimento> suggerimentiDaCasa = suggerimentiPerAccuratezza.get(accuratezza);
		return suggerimentiDaCasa.get(random.nextInt(suggerimentiDaCasa.size()));
	}

	public static QuesitoBuilder builder() {
		return new QuesitoBuilder();
	}

	public List<Suggerimento> getSuggerimenti() {
		List<Suggerimento> suggerimenti = new ArrayList<>();
		for (List<Suggerimento> suggerimentiSelezionati : suggerimentiPerAccuratezza.values()) {
			suggerimenti.addAll(suggerimentiSelezionati);
		}
		return suggerimenti;
	}

	public Map<Accuratezza, List<Suggerimento>> getSuggerimentiPerAccuratezza() {
		return suggerimentiPerAccuratezza;
	}

	public LetteraRisposta getLetteraRispostaSbagliata() {
		List<LetteraRisposta> lettereRispostaErrate = new ArrayList<>();

		for (Risposta risposta : getRisposteDisponibili()) {
			if (!risposta.isCorretta()) {
				lettereRispostaErrate.add(risposta.getLettera());
			}
		}
		return lettereRispostaErrate.get(random.nextInt(lettereRispostaErrate.size()));
	}

	public LetteraRisposta getLetteraRispostaCorretta() {
		LetteraRisposta letteraRisposta = null;
		for (Risposta risposta : getRisposteDisponibili()) {
			if (risposta.isCorretta()) {
				letteraRisposta = risposta.getLettera();
			}
		}
		return letteraRisposta;
	}
	
	public String getTestoRispostaSbagliata() {
		String testo = "";
		for (Risposta risposta : getRisposteDisponibili()) {
			if (!risposta.isCorretta()) {
				testo = risposta.getTesto();
			}
		}
		return testo;
	}

	public String getTestoRispostaCorretta() {
		String testo = "";
		for (Risposta risposta : getRisposteDisponibili()) {
			if (risposta.isCorretta()) {
				testo = risposta.getTesto();
			}
		}
		return testo;
	}

	public static class QuesitoBuilder {
		private static final int NUMERO_RISPOSTE = 4;

		private Domanda domanda;
		private Risposta[] risposte = new Risposta[NUMERO_RISPOSTE];
		private Difficolta difficolta;
		private Map<Accuratezza, List<Suggerimento>> suggerimentiPerAccuratezza = new HashMap<>();

		private int indiceRisposte = 0;

		private QuesitoBuilder() {
		}

		public void setDomanda(Domanda domanda) {
			this.domanda = domanda;
		}

		public void setDifficolta(Difficolta difficolta) {
			this.difficolta = difficolta;
		}

		/**
		 * Permette di agggiungere una risposta fino a che non viene superato il numero
		 * massimo consentito
		 * 
		 * @param risposta
		 * @throws NumeroMassimoRisposteSuperatoException
		 */
		public void aggiungiRisposta(Risposta risposta) throws NumeroMassimoRisposteSuperatoException {
			if (indiceRisposte == NUMERO_RISPOSTE) {
				throw new NumeroMassimoRisposteSuperatoException();
			}
			LetteraRisposta letteraRisposta = LetteraRisposta.values()[indiceRisposte];
			risposta.setLettera(letteraRisposta);

			risposte[indiceRisposte++] = risposta;
		}

		public void aggiungiSuggerimento(Suggerimento suggerimentoDaCasa) {

			Accuratezza accuratezza = suggerimentoDaCasa.getAccuratezza();

			List<Suggerimento> suggerimentiPerAccuratezza = this.suggerimentiPerAccuratezza.get(accuratezza);

			if (suggerimentiPerAccuratezza == null) {
				suggerimentiPerAccuratezza = new ArrayList<>();
				this.suggerimentiPerAccuratezza.put(accuratezza, suggerimentiPerAccuratezza);
			}

			suggerimentiPerAccuratezza.add(suggerimentoDaCasa);
		}

		/**
		 * Crea un Quesito, la domanda e la difficolta devono essere entrambe
		 * valorizzate, devono essere presenti tutte le risposte previste di cui una
		 * sola corretta
		 * 
		 * @return
		 * @throws CreazioneQuesitoException
		 * @throws SuggerimentiInvalidiException
		 */
		public Quesito build(CodiceQuesito codice) throws CreazioneQuesitoException, SuggerimentiInvalidiException {
			Quesito quesito = build();
			quesito.codice = codice;
			return quesito;
		}

		public Quesito build() throws CreazioneQuesitoException, SuggerimentiInvalidiException {
			if (domanda == null) {
				throw CreazioneQuesitoException.testoAssente();
			}
			if (difficolta == null) {
				throw CreazioneQuesitoException.difficolaAssente();
			}

			checkRisposteValide(Arrays.asList(this.risposte));
			checkSuggerimentiValidi();

			return new Quesito(domanda, risposte, difficolta, suggerimentiPerAccuratezza);
		}

		private void checkSuggerimentiValidi() throws SuggerimentiInvalidiException {
			Set<Accuratezza> keys = this.suggerimentiPerAccuratezza.keySet();
			if (keys.size() != Accuratezza.values().length) {
				throw new SuggerimentiInvalidiException();
			}

		}

		public static int getNumeroMassimoRisposte() {
			return NUMERO_RISPOSTE;
		}

	}

	public Domanda getDomanda() {
		return domanda;
	}

	public Risposta[] getRisposte() {
		return risposte;
	}

	public Difficolta getDifficolta() {
		return difficolta;
	}

	public CodiceQuesito getCodice() {
		return codice;
	}

	public Valore getValore() {
		return valore;
	}

	public static void checkRisposteValide(Collection<Risposta> risposte) throws RisposteInvalideException {
		int risposteCorrettePresenti = 0;

		for (Risposta risposta : risposte) {
			if (risposta == null) {
				throw RisposteInvalideException.presenteRispostaNulla();
			}
			if (risposta.isCorretta()) {
				risposteCorrettePresenti++;
			}
		}
		if (risposteCorrettePresenti != 1) {
			throw RisposteInvalideException.rispostaEsattaUnivocaAssente();
		}
	}

	public Collection<Risposta> getRisposteDisponibili() {
		Collection<Risposta> risposteDisponibili = new ArrayList<Risposta>();

		for (Risposta risposta : this.risposte) {
			if (risposta != null) {
				risposteDisponibili.add(risposta);
			}
		}
		return risposteDisponibili;
	}


}