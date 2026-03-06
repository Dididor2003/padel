package com.padelhub.padel_app.security;

import com.padelhub.padel_app.model.User;
import com.padelhub.padel_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

// classe pont entre Spring Security i la bd. Spring Security la crida quan necessita carregar un usuari
@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // busca l'usuari per email, si no existeix Spring capta l'excepció i retorna 401
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuari no trobat: " + email));

        // Spring Security exigeix el prefix ROLE_ per als rols (ROLE_ADMIN, ROLE_USER...)
        String rol = "ROLE_" + (user.getRol() != null ? user.getRol().name() : "USER");

        // no podem retornar el nostre User de MongoDB directament perquè Spring necessita un UserDetails.
        // usem el User de Spring Security (nom complet per evitar conflicte amb el nostre model User)
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(rol))
        );
    }
}