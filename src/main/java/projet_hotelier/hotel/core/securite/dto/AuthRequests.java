package projet_hotelier.hotel.core.securite.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTOs regroupés pour l'authentification.
 */
public class AuthRequests {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        @NotBlank private String username;
        @NotBlank private String password;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {
        @NotBlank @Size(max = 60) private String username;
        @NotBlank @Email  private String email;
        @NotBlank @Size(min = 8, max = 100) private String password;
        @NotBlank private String nom;
        @NotBlank private String prenoms;
        @NotBlank private String telephone;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthResponse {
        private String accessToken;
        private String tokenType;
        private long expiresInMs;
        private String username;
    }
}
