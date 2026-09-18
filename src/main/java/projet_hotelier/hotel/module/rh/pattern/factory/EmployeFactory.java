package projet_hotelier.hotel.module.rh.pattern.factory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import projet_hotelier.hotel.module.rh.dto.request.employe.CreateEmployeRequest;
import projet_hotelier.hotel.module.rh.model.personnel.EmployeModel;
import projet_hotelier.hotel.module.rh.pattern.strategy.ValidationStrategy;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Factory Pattern pour la creation d'entites Employe.
 * Centralise la logique de creation avec validation et initialisation.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EmployeFactory {

    private final ValidationStrategy validationStrategy;

    /**
     * Cree une nouvelle entite Employe a partir d'une requete.
     */
    public EmployeModel createEmploye(CreateEmployeRequest request, Long organisationId, Long hotelId, String username) {
        log.debug("Creation d'un employe via Factory pour l'organisation {}", organisationId);

        // Validation via Strategy Pattern
        validationStrategy.validate(request);

        // Creation de l'entite avec initialisation complete
        EmployeModel employe = new EmployeModel();
        employe.setMatricule(request.getMatricule());
        employe.setNom(request.getNom());
        employe.setPrenom(request.getPrenom());
        employe.setDateNaissance(request.getDateNaissance());
        employe.setSexe(request.getSexe());
        employe.setEmail(request.getEmail());
        employe.setTelephone(request.getTelephone());
        employe.setAdresse(request.getAdresse());
        employe.setPoste(request.getPoste());
        employe.setDepartement(request.getDepartement());
        employe.setDateEmbauche(request.getDateEmbauche());
        employe.setStatutEmploye(request.getStatutEmploye());
        employe.setTypeContrat(request.getTypeContrat());
        employe.setSalaireBase(request.getSalaireBase());
        employe.setCnpsNumero(request.getCnpsNumero());
        employe.setNif(request.getNif());
        employe.setNationalite(request.getNationalite());
        employe.setBanque(request.getBanque());
        employe.setRib(request.getRib());
        employe.setContactUrgenceNom(request.getContactUrgenceNom());
        employe.setContactUrgenceTelephone(request.getContactUrgenceTelephone());
        employe.setNotesInternes(request.getNotesInternes());
        employe.setRgpdApplicable(request.isRgpdApplicable());
        employe.setDonneesSensibles(request.isDonneesSensibles());
        employe.setBaseLegale(request.getBaseLegale());
        employe.setRetention(request.getRetention());

        // Initialisation des champs systeme
        employe.setUuid(UUID.randomUUID().toString());
        employe.setOrganisationId(organisationId);
        employe.setHotelId(hotelId);
        employe.setCreePar(username);
        employe.setDateCreation(LocalDateTime.now());
        employe.setActif(true);
        employe.setSupprime(false);

        // Tracabilite
        employe.setTraceId(request.getTraceId());
        employe.setSpanId(request.getSpanId());
        employe.setCorrelationId(request.getCorrelationId());
        employe.setRequestId(request.getRequestId());
        employe.setOperationId(request.getOperationId());
        employe.setIdempotencyKey(request.getIdempotencyKey());
        employe.setSourceSystem(request.getSourceSystem());
        employe.setSourceIp(request.getSourceIp());
        employe.setUserAgent(request.getUserAgent());

        log.debug("Employe cree via Factory: matricule={}, uuid={}", employe.getMatricule(), employe.getUuid());
        return employe;
    }
}
