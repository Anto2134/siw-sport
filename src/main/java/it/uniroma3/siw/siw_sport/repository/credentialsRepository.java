package it.uniroma3.siw.siw_sport.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.siw_sport.model.Credentials;

public interface credentialsRepository extends CrudRepository<Credentials, Long> {
    Optional<Credentials> findByUsername(String username);

}
