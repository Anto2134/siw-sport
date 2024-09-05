package it.uniroma3.siw.siw_sport;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.siw_sport.model.Squadra;
import it.uniroma3.siw.siw_sport.service.squadraService;

@Component
public class squadraValidator implements Validator {
    @Autowired
    private squadraService squadraService;

    @Override
    public void validate(Object o, Errors errors) {
        Squadra squadra = (Squadra) o;
        if (squadra.getNome() != null && squadraService.existByName(squadra.getNome()))
            errors.reject("squadra.duplicata");
    }

    public boolean supports(Class<?> aClass) {
        return Squadra.class.equals(aClass);
    }
}
