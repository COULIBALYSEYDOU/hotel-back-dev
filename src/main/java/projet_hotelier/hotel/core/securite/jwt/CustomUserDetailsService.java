package projet_hotelier.hotel.core.securite.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.core.securite.Utilisateur;
import projet_hotelier.hotel.core.securite.repository.UtilisateurRepository;

import java.util.Collections;

/**
 * Adaptateur entre l'entite Utilisateur et Spring Security UserDetails.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur user = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable : " + username));

        return new User(
                user.getUsername(),
                user.getPasswordHash(),
                Boolean.FALSE.equals(user.getVerrouille()) || user.getVerrouille() == null,
                true,
                true,
                Boolean.FALSE.equals(user.getVerrouille()) || user.getVerrouille() == null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
