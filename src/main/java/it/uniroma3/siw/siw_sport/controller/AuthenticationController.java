package it.uniroma3.siw.siw_sport.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.siw_sport.model.Credentials;
import it.uniroma3.siw.siw_sport.model.Presidente;
import it.uniroma3.siw.siw_sport.service.presidenteService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	
	@Autowired
	private it.uniroma3.siw.siw_sport.service.credentialsService credentialsService;


    @Autowired
    private presidenteService presidenteService;

	@GetMapping(value = "/register") 
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new Presidente());
		model.addAttribute("credentials", new Credentials());
		return "formRegisterUser.html";
	}
	
	@PostMapping(value = { "/register" })
    public String registerUser(@Valid @ModelAttribute("user") Presidente user,
                               BindingResult userBindingResult,
                               @Valid @ModelAttribute("credentials") Credentials credentials,
                               BindingResult credentialsBindingResult,
                               Model model) {
        // Se user e credentials hanno entrambi contenuti validi, memorizza User e Credentials nel DB
        if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
            // Salva user
            presidenteService.savePresidente(user);
            // Associa user alle credenziali e salva le credenziali
            credentials.setUtente(user);
            credentialsService.saveCredentials(credentials);
            model.addAttribute("user", user);
            return "registrationSuccesfull.html";
        }
        return "formRegisterUser";
    }

	@GetMapping(value = "/login") 
	public String showLoginForm (Model model) {
		return "formLogin";
	}

	@GetMapping(value = "/") 
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
	        return "index.html";
		}
		else {		
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			if (credentials.getRuolo().equals(Credentials.RUOLO_ADMIN)) {
				return "admin/indexAdmin.html";
			}
			if (credentials.getRuolo().equals(Credentials.RUOLO_USER)) {
				return "user/indexUser.html";
			}
		}
        return "index.html";
	}
		
    @GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRuolo().equals(Credentials.RUOLO_ADMIN)) {
            return "admin/indexAdmin.html";
        }
    	if (credentials.getRuolo().equals(Credentials.RUOLO_USER)) {
			return "user/indexUser.html";
		}
        return "index.html";
    }
}
