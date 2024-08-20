package it.uniroma3.siw.siw_sport.repository;

import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.siw_sport.model.Giocatore;
import it.uniroma3.siw.siw_sport.model.Squadra;

public interface giocatoreRepository extends CrudRepository<Giocatore, Long> {
    Iterable<Giocatore> findBySquadra(Squadra squadra);
}
