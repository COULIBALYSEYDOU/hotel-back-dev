package projet_hotelier.hotel.core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import projet_hotelier.hotel.core.securite.jwt.CustomUserDetailsService;
import projet_hotelier.hotel.core.securite.jwt.JwtAuthenticationFilter;

/**
 * Configuration de sécurité — JWT stateless + BCrypt.
 *
 * Whitelist (accès public) :
 *  - /api/v1/auth/**  (login, register)
 *  - /api-docs, /swagger-ui/**  (documentation OpenAPI)
 *  - /actuator/health, /actuator/info
 *  - /rh/**  (IHM Thymeleaf du module RH — session Spring classique)
 *  - Ressources statiques (/css/**, /js/**, /images/**)
 *
 * Tout le reste (endpoints /api/**) requiert un JWT valide en header Authorization: Bearer <token>.
 *
 * En dev, la propriete app.security.permit-all=true permet de tout ouvrir temporairement.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService userDetailsService;

    @Value("${app.security.permit-all:false}")
    private boolean permitAll;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> {
                if (permitAll) {
                    auth.anyRequest().permitAll();
                    return;
                }
                auth
                    .requestMatchers(
                            "/api/v1/auth/**",
                            "/api-docs", "/api-docs/**",
                            "/swagger-ui.html", "/swagger-ui/**",
                            "/actuator/health", "/actuator/info",
                            "/rh/**",
                            "/css/**", "/js/**", "/images/**", "/webjars/**",
                            "/error", "/favicon.ico"
                    ).permitAll()
                    .anyRequest().authenticated();
            })
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
