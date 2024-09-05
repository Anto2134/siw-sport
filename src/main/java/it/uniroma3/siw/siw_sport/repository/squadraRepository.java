package it.uniroma3.siw.siw_sport.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.siw_sport.model.Presidente;
import it.uniroma3.siw.siw_sport.model.Squadra;
import java.util.List;



public interface squadraRepository extends CrudRepository<Squadra, Long> {
    List<Squadra> findByPresidente(Presidente presidente);
    boolean existsByNome(String nome);
}
