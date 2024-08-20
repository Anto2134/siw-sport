package it.uniroma3.siw.siw_sport.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Squadra {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String nome;
    private String indirizzo;
    private LocalDate annoFondazione;
    @OneToMany(mappedBy = "squadra")
    private List<Giocatore> giocatori;

    @OneToOne
    @JoinColumn(name = "presidente_id", referencedColumnName = "id", nullable = false)
    private Presidente presidente;

    public Presidente getPresidente() {
        return presidente;
    }

    public void setPresidente(Presidente presidente) {
        this.presidente = presidente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public LocalDate getAnnoFondazione() {
        return annoFondazione;
    }

    public void setAnnoFondazione(LocalDate annoFondazione) {
        this.annoFondazione = annoFondazione;
    }
}
