package it.uniroma3.siw.siw_sport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siw.siw_sport.giocatoreValidator;
import it.uniroma3.siw.siw_sport.model.Credentials;
import it.uniroma3.siw.siw_sport.model.Giocatore;
import it.uniroma3.siw.siw_sport.model.Presidente;
import it.uniroma3.siw.siw_sport.model.Squadra;
import it.uniroma3.siw.siw_sport.service.credentialsService;
import it.uniroma3.siw.siw_sport.service.giocatoreService;
import it.uniroma3.siw.siw_sport.service.presidenteService;
import it.uniroma3.siw.siw_sport.service.squadraService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class presidenteController {
    @Autowired
    presidenteService presidenteService;

    @Autowired
    credentialsService credentialsService;

    @Autowired
    squadraService squadraService;

    @Autowired
    giocatoreService giocatoreService;

    @Autowired
    giocatoreValidator giocatoreValidator;

    @GetMapping("/presidente/{id}")
    public String getpresidente(@PathVariable("id") Long id, Model model) {
        model.addAttribute("presidente", this.presidenteService.findById(id));
        return "presidente.html";
    }

    @GetMapping("/presidente")
    public String showPresidenti(Model model) {
        model.addAttribute("presidenti", this.presidenteService.findAll());
        return "presidenti.html";
    }

    @GetMapping("/user/gestioneGiocatori/")
    public String gestisciGiocatori(Model model) {
        return "/user/gestioneSquadra";
    }

    @GetMapping("/user/giocatori/")
    public String getGiocatori(Model model) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("L'utente non è autenticato");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (userDetails == null) {
            throw new IllegalStateException("Dettagli utente non trovati");
        }

        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        if (credentials == null) {
            throw new IllegalStateException("Credenziali non trovate per l'utente: " + userDetails.getUsername());
        }

        Presidente currentUser = credentials.getUtente();
        if (currentUser == null) {
            throw new IllegalStateException("Presidente non trovato per le credenziali");
        }

        Iterable<Squadra> app = squadraService.findByPresidente(currentUser);
        if (app == null || !app.iterator().hasNext()) {
            return "/user/nessunaSquadra.html";
            // throw new IllegalStateException("Nessuna squadra trovata per il presidente
            // corrente");
        }

        Squadra squadraCorrente = app.iterator().next();
        model.addAttribute("giocatori", giocatoreService.findBySquadra(squadraCorrente));
        return "/user/visualizzaGiocatori";
    }

    @GetMapping("/user/aggiuntaGiocatori/")
    public String aggiuntaGiocatori(Model model) {
        model.addAttribute("aggiuntaGiocatori", giocatoreService.findAll());
        return "/user/aggiungiGiocatori";
    }

    @GetMapping("/user/giocatore/ingaggia/{id}")
    public String ingaggiaGiocatore(@PathVariable Long id, Model model) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        Presidente currentUser = credentials.getUtente();
        // Recupera la squadra dell'utente corrente
        Iterable<Squadra> squadre = squadraService.findByPresidente(currentUser);
        if (squadre == null || !squadre.iterator().hasNext()) {
            return "/user/nessunaSquadra.html";
            // throw new IllegalStateException("Nessuna squadra trovata per il presidente
            // corrente");
        }
        Squadra squadraCorrente = squadre.iterator().next();
        Giocatore giocatore = giocatoreService.findById(id);
        giocatore.setSquadra(squadraCorrente);
        giocatore.setSvincolato(false);// Associa il giocatore alla squadra
        // Salva il giocatore nel database
        giocatoreService.save(giocatore);
        return "redirect:/user/aggiuntaGiocatori/";
    }

    @GetMapping("user/giocatore/crea")
    public String creaGiocatore(Model model) {
        model.addAttribute("giocatore", new Giocatore());
        return "user/formNuovoGiocatore";
    }

    @PostMapping("user/giocatore/crea")
    public String saveGiocatore(@Valid @ModelAttribute Giocatore giocatore, BindingResult bindingResult, Model model) {
        // Recupera l'utente corrente
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        Presidente currentUser = credentials.getUtente();
        // Recupera la squadra dell'utente corrente
        Iterable<Squadra> squadre = squadraService.findByPresidente(currentUser);
        if (squadre == null || !squadre.iterator().hasNext()) {
            return "/user/nessunaSquadra.html";
            // throw new IllegalStateException("Nessuna squadra trovata per il presidente
            // corrente");
        }
        giocatoreValidator.validate(giocatore, bindingResult);
        if (bindingResult.hasErrors())
            return "user/formNuovoGiocatore";
        Squadra squadraCorrente = squadre.iterator().next();
        giocatore.setSquadra(squadraCorrente); // Associa il giocatore alla squadra
        giocatore.setSvincolato(false);
        // Salva il giocatore nel database
        giocatoreService.save(giocatore);

        return "redirect:/user/gestioneGiocatori/"; // Reindirizza alla pagina di visualizzazione giocatori

    }

    @GetMapping("/user/rimuoviGiocatori/")
    public String rimuoviGiocatori(Model model) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        Presidente currentUser = credentials.getUtente();
        // Recupera la squadra dell'utente corrente
        Iterable<Squadra> squadre = squadraService.findByPresidente(currentUser);
        if (squadre == null || !squadre.iterator().hasNext()) {
            return "/user/nessunaSquadra.html";
            // throw new IllegalStateException("Nessuna squadra trovata per il presidente
            // corrente");
        }
        Squadra squadraCorrente = squadre.iterator().next();
        model.addAttribute("rimuoviGiocatori", giocatoreService.findBySquadra(squadraCorrente));
        return "user/rimuoviGiocatori";
    }

    @GetMapping("/user/giocatore/svincola/{id}")
    public String svincolaGiocatore(Model model, @PathVariable Long id) {
        Giocatore giocatore = giocatoreService.findById(id);
        model.addAttribute("giocatore", giocatore);
        return "/user/formGiocatoreSvincolato";
    }

    @PostMapping("/user/giocatore/svincola/si/{id}")
    public String giocatoreSvincolato(Model model, @PathVariable Long id) {
        giocatoreService.svincola(id);
        return "redirect:/user/rimuoviGiocatori/";
    }

}
