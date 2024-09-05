package it.uniroma3.siw.siw_sport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siw_sport.model.Giocatore;
import it.uniroma3.siw.siw_sport.model.Squadra;
import it.uniroma3.siw.siw_sport.repository.giocatoreRepository;

@Service
public class giocatoreService {
    @Autowired
    private giocatoreRepository giocatoreRepository;

    public Giocatore findById(Long id) {
        return giocatoreRepository.findById(id).get();
    }

    public Iterable<Giocatore> findAll() {
        return giocatoreRepository.findAll();
    }

    public Iterable<Giocatore> findBySquadra(Squadra squadra) {
        return giocatoreRepository.findBySquadra(squadra);
    }

    public Giocatore save(Giocatore giocatore) {
        return giocatoreRepository.save(giocatore);
    }

    public void svincola(Long id) {
        Giocatore giocatore = giocatoreRepository.findById(id).get();
        giocatore.setSquadra(null);
        giocatore.setSvincolato(true);
        giocatoreRepository.save(giocatore);
    }

    public boolean checkDate(Giocatore g) {
        if (g.getFineTesseramento().isBefore(g.getInizioTesseramento()))
            return false;
        return true;
    }
}
