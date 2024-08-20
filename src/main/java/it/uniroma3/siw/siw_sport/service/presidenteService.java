package it.uniroma3.siw.siw_sport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siw_sport.model.Presidente;
import it.uniroma3.siw.siw_sport.repository.presidenteRepository;

@Service
public class presidenteService {
    @Autowired
    private presidenteRepository presidenteRepository;

    public Presidente findById(Long id) {
        return presidenteRepository.findById(id).get();
    }

    public Iterable<Presidente> findAll() {
        return presidenteRepository.findAll();
    }

    public Presidente savePresidente(Presidente presidente) {
        return presidenteRepository.save(presidente);
    }
}