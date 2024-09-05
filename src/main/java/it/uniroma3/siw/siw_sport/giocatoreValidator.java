package it.uniroma3.siw.siw_sport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.siw_sport.model.Giocatore;
import it.uniroma3.siw.siw_sport.service.giocatoreService;

@Component
public class giocatoreValidator implements Validator {
    @Autowired
    private giocatoreService giocatoreService;

    @Override
    public void validate(Object o, Errors e) {
        Giocatore g = (Giocatore) o;
        if (g.getInizioTesseramento() != null && g.getFineTesseramento() != null && !giocatoreService.checkDate(g))
            e.reject("data.sbagliata");
    }

    public boolean supports(Class<?> aClass) {
        return Giocatore.class.equals(aClass);
    }

}
