package it.uniroma3.siw.siw_sport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import it.uniroma3.siw.siw_sport.service.giocatoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class giocatoreController {
    @Autowired
    giocatoreService giocatoreService;

    @GetMapping("/giocatore/{id}")
    public String getGiocatore(@PathVariable("id") Long id, Model model) {
        model.addAttribute("giocatore", this.giocatoreService.findById(id));
        return "giocatore.html";
    }

    @GetMapping("/giocatore")
    public String showGiocatori(Model model) {
        model.addAttribute("giocatori", this.giocatoreService.findAll());
        return "giocatori.html";
    }

}
