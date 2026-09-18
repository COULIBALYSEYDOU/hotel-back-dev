package projet_hotelier.hotel.core.securite.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.core.securite.Utilisateur;
import projet_hotelier.hotel.core.securite.dto.AuthRequests.AuthResponse;
import projet_hotelier.hotel.core.securite.dto.AuthRequests.LoginRequest;
import projet_hotelier.hotel.core.securite.dto.AuthRequests.RegisterRequest;
import projet_hotelier.hotel.core.securite.jwt.CustomUserDetailsService;
import projet_hotelier.hotel.core.securite.jwt.JwtService;
import projet_hotelier.hotel.core.securite.repository.UtilisateurRepository;
import projet_hotelier.hotel.shared.dto.ApiResponse;
import projet_hotelier.hotel.shared.exception.ConflictException;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.jwt.expiration-ms:86400000}")
    private long expirationMs;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        UserDetails user = userDetailsService.loadUserByUsername(req.getUsername());
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(ApiResponse.success(AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresInMs(expirationMs)
                .username(user.getUsername())
                .build()));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest req) {
        if (utilisateurRepository.existsByUsername(req.getUsername())) {
            throw ConflictException.duplicate("Utilisateur", "username", req.getUsername());
        }
        if (utilisateurRepository.existsByEmail(req.getEmail())) {
            throw ConflictException.duplicate("Utilisateur", "email", req.getEmail());
        }
        Utilisateur u = new Utilisateur();
        u.setUsername(req.getUsername());
        u.setEmail(req.getEmail());
        u.setNom(req.getNom());
        u.setPrenoms(req.getPrenoms());
        u.setTelephone(req.getTelephone());
        u.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        u.setVerrouille(false);
        u.setActif(true);
        u.setSupprime(false);
        utilisateurRepository.save(u);
        log.info("Nouvel utilisateur enregistre : {}", req.getUsername());

        UserDetails user = userDetailsService.loadUserByUsername(req.getUsername());
        String token = jwtService.generateToken(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresInMs(expirationMs)
                .username(user.getUsername())
                .build(), "Utilisateur enregistre avec succes"));
    }
}
