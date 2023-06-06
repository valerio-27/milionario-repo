package it.academy.gaming.milionario.core.application;

import it.academy.gaming.milionario.core.application.commands.IniziaPartitaCommand;
import it.academy.gaming.milionario.core.application.views.PartitaGiocataView;
import it.academy.gaming.milionario.core.application.views.PartitaView;
import it.academy.gaming.milionario.core.application.views.SuggerimentoView;
import it.academy.gaming.milionario.core.application.views.VotazioneView;
import it.academy.gaming.milionario.core.domain.Giocatore;
import it.academy.gaming.milionario.core.domain.OpzioniPersonaRepository;
import it.academy.gaming.milionario.core.domain.Partita;
import it.academy.gaming.milionario.core.domain.PartitaGiocata;
import it.academy.gaming.milionario.core.domain.PartitaGiocataRepository;
import it.academy.gaming.milionario.core.domain.QuesitoRepository;
import it.academy.gaming.milionario.core.domain.Suggerimento;
import it.academy.gaming.milionario.core.domain.Votazione;
import it.academy.gaming.milionario.core.domain.exceptions.AiutoNonDisponibileException;
import it.academy.gaming.milionario.core.domain.exceptions.NomeNonValidoException;
import it.academy.gaming.milionario.core.domain.exceptions.PartitaException;

public class MilionarioService {

	public static void main(String[] args) {

	}

	private Partita partita;

	public MilionarioService(QuesitoRepository quesitoRepository, OpzioniPersonaRepository opzioniPersonaRepository,
			PartitaGiocataRepository partitaGiocataRepository) {
		this.partita = new Partita(quesitoRepository, opzioniPersonaRepository, partitaGiocataRepository);
	}

	public PartitaView iniziaPartita(IniziaPartitaCommand command) throws PartitaException, NomeNonValidoException {
		partita.inizia(new Giocatore(command.getNome()));
		return new PartitaView(partita);
	}

	public PartitaView usaAiutoComputer() throws AiutoNonDisponibileException {
		partita.usaAiutoComputer();
		return new PartitaView(partita);
	}

	public SuggerimentoView usaAiutoCasa() throws AiutoNonDisponibileException {
		Suggerimento suggerimento = partita.usaAiutoCasa();

		return new SuggerimentoView(suggerimento);
	}

	public VotazioneView usaAiutoPubblico() throws AiutoNonDisponibileException {
		Votazione votazione = this.partita.usaAiutoPubblico();
		return new VotazioneView(votazione);
	}

	public PartitaView indovina(IndovinaRispostaCommand command) throws PartitaException {
		this.partita.indovina(command.getLetteraRisposta());
		return new PartitaView(partita);
	}

	public PartitaView continua() throws PartitaException {
		this.partita.continua();
		return new PartitaView(partita);
	}

	public PartitaGiocataView ritirati() throws PartitaException {
		PartitaGiocata partitaGiocata = this.partita.ritirati();
		return new PartitaGiocataView(partitaGiocata);
	}

}