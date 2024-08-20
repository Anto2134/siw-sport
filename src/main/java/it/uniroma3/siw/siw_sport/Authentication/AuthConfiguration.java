package it.uniroma3.siw.siw_sport.Authentication;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import it.uniroma3.siw.siw_sport.model.Credentials;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .authoritiesByUsernameQuery("SELECT username, ruolo from credentials WHERE username=?")
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().and().cors().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/", "/index", "/register", "/registrationSuccessful", "/css/**",
                        "/images/**", "/favicon.ico", "/display/**")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "/squadre/**", "/squadra/**")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/register", "/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/admin/**").hasAuthority(Credentials.RUOLO_ADMIN)
                .requestMatchers(HttpMethod.POST, "/admin/**").hasAuthority(Credentials.RUOLO_ADMIN)
                // .requestMatchers(HttpMethod.GET, "/prenotazione/**").hasAuthority(Credentials.RUOLO_USER)
                // .requestMatchers(HttpMethod.GET, "/prenotazione/**").hasAuthority(Credentials.RUOLO_ADMIN)
                // .requestMatchers(HttpMethod.POST, "/prenotazione/**").hasAuthority(Credentials.RUOLO_USER)
                .requestMatchers(HttpMethod.POST, "/admin/squadra/crea").hasAuthority(Credentials.RUOLO_ADMIN)
                .requestMatchers(HttpMethod.GET, "/admin/squadra/crea").hasAuthority(Credentials.RUOLO_ADMIN)
                .requestMatchers(HttpMethod.GET, "/user/**")
                .hasAnyAuthority(Credentials.RUOLO_USER)
                .requestMatchers(HttpMethod.POST, "/user/**")
                .hasAnyAuthority(Credentials.RUOLO_USER)
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/success", true)
                .failureUrl("/login?error=true")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .clearAuthentication(true).permitAll();
        return httpSecurity.build();
    }

}
