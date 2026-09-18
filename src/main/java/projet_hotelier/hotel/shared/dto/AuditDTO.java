package projet_hotelier.hotel.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO pour les informations d'audit communes a toutes les entites.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuditDTO {

    private String uuid;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private String creePar;
    private String modifiePar;
    private Long version;
    private Boolean actif;

    public static AuditDTO of(String uuid, LocalDateTime dateCreation, LocalDateTime dateModification,
                              String creePar, String modifiePar, Long version, Boolean actif) {
        return AuditDTO.builder()
                .uuid(uuid)
                .dateCreation(dateCreation)
                .dateModification(dateModification)
                .creePar(creePar)
                .modifiePar(modifiePar)
                .version(version)
                .actif(actif)
                .build();
    }
}
