package it.uniroma3.siw.siw_sport.service;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siw_sport.model.Presidente;
import it.uniroma3.siw.siw_sport.model.Squadra;
import it.uniroma3.siw.siw_sport.repository.squadraRepository;

@Service
public class squadraService {
    @Autowired
    private squadraRepository squadraRepository;

    public Squadra findById(Long id) {
        return this.squadraRepository.findById(id).get();
    }

    public Iterable<Squadra> findAll() {
        return squadraRepository.findAll();
    }

    public Squadra saveSquadra(Squadra squadra) {
        return squadraRepository.save(squadra);
    }

    public Iterable<Squadra> findByPresidente(Presidente presidente) {
        return squadraRepository.findByPresidente(presidente);
    }
}
