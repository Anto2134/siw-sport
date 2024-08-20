package it.uniroma3.siw.siw_sport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.siw_sport.model.Squadra;
import it.uniroma3.siw.siw_sport.service.presidenteService;
import it.uniroma3.siw.siw_sport.service.squadraService;

@Controller
public class squadraController {
    @Autowired
    squadraService squadraService;
    @Autowired
    presidenteService presidenteService;

    // @GetMapping("/squadra/{id}")
    // public String getSquadra(@PathVariable("id") Long id, Model model) {
    // model.addAttribute("squadra", this.squadraService.findById(id));
    // return "squadra.html";
    // }

    @GetMapping("/squadra")
    public String showSquadre(Model model) {
        model.addAttribute("squadre", this.squadraService.findAll());
        return "squadre.html";
    }

    // @GetMapping("/gestioneSquadre")
    // public String gestioneSquadre(Model model) {
    // return "formSquadre.html";
    // }

    @GetMapping("/admin/gestioneSquadre")
    public String gestioneAdmin(Model model) {
        model.addAttribute("squadre", this.squadraService.findAll());
        return "admin/gestioneSquadre";
    }

    @GetMapping("/admin/squadra/crea")
    public String creaSquadra(Model model) {
        model.addAttribute("squadra", new Squadra());
        model.addAttribute("presidenti", this.presidenteService.findAll());
        return "admin/formNuovaSquadra";
    }

    @PostMapping("/admin/squadra/crea")
    public String salvaNuovaSquadra(Squadra squadra) {
        squadraService.saveSquadra(squadra);
        return "redirect:/";
    }

    @GetMapping("/squadra/{id}")
    public String getSquadra(@PathVariable Long id, Model model) {
        model.addAttribute("squadra", this.squadraService.findById(id));
        return "dettagliSquadra.html";
    }

    @GetMapping("/admin/squadra/modifica/{id}")
    public String modificaSquadra(@PathVariable Long id, Model model) {
        Squadra squadra = this.squadraService.findById(id);
        model.addAttribute("squadra", squadra);
        model.addAttribute("presidenti", this.presidenteService.findAll());
        return "/admin/formModificaSquadra";
    }

    @PostMapping("/admin/squadra/modifica/{id}")
    public String salvaModificaSquadra(@ModelAttribute Squadra squadra, @PathVariable Long id, Model model) {
        squadraService.saveSquadra(squadra);
        return "redirect:/admin/gestioneSquadre";
    }
}
