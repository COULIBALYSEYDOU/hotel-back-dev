package projet_hotelier.hotel.module.rh.service.evaluation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.rh.domain.event.EvaluationEvent;
import projet_hotelier.hotel.module.rh.dto.request.evaluation.CreateEvaluationRequest;
import projet_hotelier.hotel.module.rh.dto.request.evaluation.UpdateEvaluationRequest;
import projet_hotelier.hotel.module.rh.dto.response.evaluation.EvaluationResponse;
import projet_hotelier.hotel.module.rh.mapper.evaluation.EvaluationMapper;
import projet_hotelier.hotel.module.rh.model.evaluation.EvaluationPerformanceModel;
import projet_hotelier.hotel.module.rh.model.personnel.EmployeModel;
import projet_hotelier.hotel.module.rh.repository.evaluation.EvaluationRepository;
import projet_hotelier.hotel.module.rh.repository.employe.EmployeRepository;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Service enterprise-grade pour la gestion des évaluations de performance.
 * Workflow complet avec calculs de scores, recommandations, conformité légale.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EvaluationPerformanceService {

    private final EvaluationRepository evaluationRepository;
    private final EmployeRepository employeRepository;
    private final EvaluationMapper evaluationMapper;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Démarre une nouvelle évaluation de performance.
     */
    public EvaluationResponse demarrerEvaluation(CreateEvaluationRequest request, Long organisationId, 
                                                 Long hotelId, String username) {
        log.info("Démarrage d'une évaluation pour l'employé: {}", request.getEmployeId());

        // Validation
        EmployeModel employe = trouverEmploye(request.getEmployeId(), organisationId);
        validerPreconditionsEvaluation(employe, request);

        // Création de l'évaluation
        EvaluationPerformanceModel entity = evaluationMapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);
        entity.setStatutEvaluation("EN_COURS");

        // Calcul de la période
        if (entity.getPeriodeDebut() == null) {
            entity.setPeriodeDebut(calculerPeriodeDebut(entity.getTypeEvaluation()));
        }
        if (entity.getPeriodeFin() == null) {
            entity.setPeriodeFin(calculerPeriodeFin(entity.getTypeEvaluation(), entity.getPeriodeDebut()));
        }

        EvaluationPerformanceModel saved = evaluationRepository.save(entity);

        // Mettre à jour l'employé
        employe.setDateDerniereEvaluation(LocalDate.now());
        employe.setStatutEvaluation("EN_COURS");
        employeRepository.save(employe);

        // Déclencher événement
        eventPublisher.publishEvent(
            EvaluationEvent.evaluationDemarree(employe.getUuid(), employe.getId(), organisationId,
                                               request.getTypeEvaluation(), request.getEvaluateurId(), username)
        );

        log.info("Évaluation démarrée avec succès: id={}, uuid={}", saved.getId(), saved.getUuid());
        return evaluationMapper.toResponse(saved);
    }

    /**
     * Complète une évaluation avec les scores et commentaires.
     */
    public EvaluationResponse completerEvaluation(String uuid, UpdateEvaluationRequest request, 
                                                   Long organisationId, String username) {
        log.info("Complétion de l'évaluation: {}", uuid);

        EvaluationPerformanceModel entity = trouverEvaluation(uuid, organisationId);
        EmployeModel employe = trouverEmploye(entity.getEmployeId(), organisationId);

        // Mise à jour des données
        evaluationMapper.updateEntity(entity, request);

        // Calcul des scores
        calculerScores(entity, request);

        // Génération des recommandations
        genererRecommandations(entity);

        // Mise à jour du statut
        entity.setStatutEvaluation("COMPLETE");
        entity.setModifiePar(username);

        EvaluationPerformanceModel updated = evaluationRepository.save(entity);

        // Mettre à jour l'employé
        employe.setScorePerformanceAnnee(entity.getScoreGlobal());
        employe.setDateDerniereEvaluation(entity.getDateEvaluation());
        employe.setStatutEvaluation("COMPLETE");
        employeRepository.save(employe);

        // Déclencher événement
        eventPublisher.publishEvent(
            EvaluationEvent.evaluationCompletee(employe.getUuid(), employe.getId(), organisationId,
                                               entity.getScoreGlobal(), username)
        );

        log.info("Évaluation complétée avec succès: id={}, uuid={}", updated.getId(), updated.getUuid());
        return evaluationMapper.toResponse(updated);
    }

    /**
     * Valide une évaluation (validation RH/Direction).
     */
    public EvaluationResponse validerEvaluation(String uuid, Long valideParId, Long organisationId, String username) {
        log.info("Validation de l'évaluation: {}", uuid);

        EvaluationPerformanceModel entity = trouverEvaluation(uuid, organisationId);
        EmployeModel employe = trouverEmploye(entity.getEmployeId(), organisationId);

        // Validation
        if (!"COMPLETE".equals(entity.getStatutEvaluation())) {
            throw new IllegalStateException("L'évaluation doit être complétée avant validation");
        }

        entity.setStatutEvaluation("VALIDEE");
        entity.setValideParId(valideParId);
        entity.setDateValidation(LocalDate.now());
        entity.setModifiePar(username);

        EvaluationPerformanceModel updated = evaluationRepository.save(entity);

        // Traitement des recommandations
        traiterRecommandations(entity, employe);

        // Déclencher événement
        eventPublisher.publishEvent(
            EvaluationEvent.evaluationValidee(employe.getUuid(), employe.getId(), organisationId,
                                               valideParId, username)
        );

        log.info("Évaluation validée avec succès: id={}, uuid={}", updated.getId(), updated.getUuid());
        return evaluationMapper.toResponse(updated);
    }

    /**
     * Calcule les scores de l'évaluation.
     */
    private void calculerScores(EvaluationPerformanceModel entity, UpdateEvaluationRequest request) {
        // Calcul du score global (moyenne pondérée)
        BigDecimal scoreCompetences = request.getScoreCompetences() != null ? 
            request.getScoreCompetences() : BigDecimal.ZERO;
        BigDecimal scoreObjectifs = request.getScoreObjectifs() != null ? 
            request.getScoreObjectifs() : BigDecimal.ZERO;
        BigDecimal scoreComportement = request.getScoreComportement() != null ? 
            request.getScoreComportement() : BigDecimal.ZERO;

        // Pondération: 40% compétences, 40% objectifs, 20% comportement
        BigDecimal scoreGlobal = scoreCompetences
            .multiply(BigDecimal.valueOf(0.4))
            .add(scoreObjectifs.multiply(BigDecimal.valueOf(0.4)))
            .add(scoreComportement.multiply(BigDecimal.valueOf(0.2)));

        entity.setScoreGlobal(scoreGlobal);
        entity.setScoreCompetences(scoreCompetences);
        entity.setScoreObjectifs(scoreObjectifs);
        entity.setScoreComportement(scoreComportement);
    }

    /**
     * Génère les recommandations basées sur les scores.
     */
    private void genererRecommandations(EvaluationPerformanceModel entity) {
        BigDecimal scoreGlobal = entity.getScoreGlobal();

        if (scoreGlobal.compareTo(BigDecimal.valueOf(90)) >= 0) {
            entity.setRecommandation("PROMOTION");
            entity.setPlanAction("Considérer pour promotion ou augmentation");
        } else if (scoreGlobal.compareTo(BigDecimal.valueOf(70)) >= 0) {
            entity.setRecommandation("MAINTIEN");
            entity.setPlanAction("Maintenir le niveau actuel, continuer le développement");
        } else if (scoreGlobal.compareTo(BigDecimal.valueOf(50)) >= 0) {
            entity.setRecommandation("FORMATION");
            entity.setPlanAction("Formation nécessaire pour améliorer les performances");
        } else {
            entity.setRecommandation("MISE_EN_GARDE");
            entity.setPlanAction("Plan d'action correctif nécessaire");
        }
    }

    /**
     * Traite les recommandations après validation.
     */
    private void traiterRecommandations(EvaluationPerformanceModel entity, EmployeModel employe) {
        if ("PROMOTION".equals(entity.getRecommandation())) {
            // Déclencher événement de promotion recommandée
            eventPublisher.publishEvent(
                EvaluationEvent.promotionRecommandee(employe.getUuid(), employe.getId(),
                                                     employe.getOrganisationId(), 
                                                     entity.getPlanAction(), "SYSTEM")
            );
        }
    }

    /**
     * Calcule la date de début de période selon le type d'évaluation.
     */
    private LocalDate calculerPeriodeDebut(String typeEvaluation) {
        LocalDate aujourdhui = LocalDate.now();
        
        switch (typeEvaluation) {
            case "ANNUEL":
                return aujourdhui.minusYears(1).withDayOfYear(1);
            case "SEMESTRIEL":
                return aujourdhui.minusMonths(6).withDayOfMonth(1);
            case "TRIMESTRIEL":
                return aujourdhui.minusMonths(3).withDayOfMonth(1);
            default:
                return aujourdhui.minusMonths(1).withDayOfMonth(1);
        }
    }

    /**
     * Calcule la date de fin de période selon le type d'évaluation.
     */
    private LocalDate calculerPeriodeFin(String typeEvaluation, LocalDate periodeDebut) {
        switch (typeEvaluation) {
            case "ANNUEL":
                return periodeDebut.plusYears(1).minusDays(1);
            case "SEMESTRIEL":
                return periodeDebut.plusMonths(6).minusDays(1);
            case "TRIMESTRIEL":
                return periodeDebut.plusMonths(3).minusDays(1);
            default:
                return periodeDebut.plusMonths(1).minusDays(1);
        }
    }

    /**
     * Valide les préconditions avant de démarrer une évaluation.
     */
    private void validerPreconditionsEvaluation(EmployeModel employe, CreateEvaluationRequest request) {
        if (!employe.estActif()) {
            throw new IllegalStateException("L'employé doit être actif pour une évaluation");
        }

        // Vérifier qu'il n'y a pas déjà une évaluation en cours
        List<EvaluationPerformanceModel> evaluationsEnCours = evaluationRepository
            .findByEmployeIdAndStatutEvaluationAndActifTrue(employe.getId(), "EN_COURS");
        
        if (!evaluationsEnCours.isEmpty()) {
            throw new IllegalStateException("Une évaluation est déjà en cours pour cet employé");
        }
    }

    @Transactional(readOnly = true)
    public EvaluationResponse getByUuid(String uuid, Long organisationId) {
        EvaluationPerformanceModel entity = trouverEvaluation(uuid, organisationId);
        return evaluationMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public Page<EvaluationResponse> getAllPaginated(Long organisationId, Pageable pageable) {
        Page<EvaluationPerformanceModel> entities = evaluationRepository
            .findByOrganisationIdAndActifTrue(organisationId, pageable);
        return entities.map(evaluationMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<EvaluationResponse> getByEmploye(Long employeId) {
        List<EvaluationPerformanceModel> entities = evaluationRepository
            .findByEmployeIdAndActifTrue(employeId);
        return evaluationMapper.toResponseList(entities);
    }

    private EvaluationPerformanceModel trouverEvaluation(String uuid, Long organisationId) {
        return evaluationRepository.findByUuid(uuid)
            .filter(e -> e.getOrganisationId().equals(organisationId))
            .filter(e -> !Boolean.TRUE.equals(e.getSupprime()))
            .orElseThrow(() -> new ResourceNotFoundException("EvaluationPerformance", uuid));
    }

    private EmployeModel trouverEmploye(Long employeId, Long organisationId) {
        return employeRepository.findById(employeId)
            .filter(e -> e.getOrganisationId().equals(organisationId))
            .filter(e -> !Boolean.TRUE.equals(e.getSupprime()))
            .orElseThrow(() -> new ResourceNotFoundException("Employe", employeId));
    }
}
