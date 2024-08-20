package it.uniroma3.siw.siw_sport.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Giocatore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String nome;
    private String cognome;
    private LocalDate dataNascita;
    private String luogoNascita;
    private String ruolo;
    private LocalDate inizioTesseramento;
    private LocalDate fineTesseramento;
    @ManyToOne
    private Squadra squadra;

    public Squadra getSquadra() {
        return squadra;
    }

    public void setSquadra(Squadra squadra) {
        this.squadra = squadra;
    }

    private boolean isSvincolato;

    public boolean isSvincolato() {
        return isSvincolato;
    }

    public void setSvincolato(boolean isSvincolato) {
        this.isSvincolato = isSvincolato;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public LocalDate getInizioTesseramento() {
        return inizioTesseramento;
    }

    public void setInizioTesseramento(LocalDate inizioTesseramento) {
        this.inizioTesseramento = inizioTesseramento;
    }

    public LocalDate getFineTesseramento() {
        return fineTesseramento;
    }

    public void setFineTesseramento(LocalDate fineTesseramento) {
        this.fineTesseramento = fineTesseramento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getLuogoNascita() {
        return luogoNascita;
    }

    public void setLuogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
    }
}
