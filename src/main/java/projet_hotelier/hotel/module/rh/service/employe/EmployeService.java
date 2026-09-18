package projet_hotelier.hotel.module.rh.service.employe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.rh.dto.request.employe.CreateEmployeRequest;
import projet_hotelier.hotel.module.rh.dto.request.employe.UpdateEmployeRequest;
import projet_hotelier.hotel.module.rh.dto.response.employe.EmployeResponse;
import projet_hotelier.hotel.module.rh.mapper.employe.EmployeMapper;
import projet_hotelier.hotel.module.rh.model.personnel.EmployeModel;
import projet_hotelier.hotel.module.rh.repository.employe.EmployeRepository;
import projet_hotelier.hotel.shared.exception.ConflictException;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Service pour la gestion des employes.
 * Gere le CRUD complet avec validation, multi-tenant et tracabilite.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EmployeService {

    private final EmployeRepository employeRepository;
    private final EmployeMapper employeMapper;

    /**
     * Cree un nouvel employe.
     */
    public EmployeResponse create(CreateEmployeRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation d'un employe pour l'organisation {} et l'hotel {}", organisationId, hotelId);

        // Verification unicite du matricule
        if (employeRepository.existsByMatricule(request.getMatricule())) {
            throw new ConflictException("EMPLOYE_MATRICULE_EXISTS",
                    "Un employe avec le matricule '" + request.getMatricule() + "' existe deja");
        }

        EmployeModel entity = employeMapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);

        EmployeModel saved = employeRepository.save(entity);
        log.info("Employe cree avec succes: id={}, uuid={}", saved.getId(), saved.getUuid());

        return employeMapper.toResponse(saved);
    }

    /**
     * Met a jour un employe existant.
     */
    public EmployeResponse update(String uuid, UpdateEmployeRequest request, Long organisationId, String username) {
        log.info("Mise a jour de l'employe avec uuid: {}", uuid);

        EmployeModel entity = findByUuidAndOrganisation(uuid, organisationId);
        employeMapper.updateEntity(entity, request);
        entity.setModifiePar(username);

        EmployeModel updated = employeRepository.save(entity);
        log.info("Employe mis a jour avec succes: id={}, uuid={}", updated.getId(), updated.getUuid());

        return employeMapper.toResponse(updated);
    }

    /**
     * Recupere un employe par son UUID.
     */
    @Transactional(readOnly = true)
    public EmployeResponse getByUuid(String uuid, Long organisationId) {
        log.debug("Recuperation de l'employe avec uuid: {}", uuid);
        EmployeModel entity = findByUuidAndOrganisation(uuid, organisationId);
        return employeMapper.toResponse(entity);
    }

    /**
     * Recupere un employe par son ID.
     */
    @Transactional(readOnly = true)
    public EmployeResponse getById(Long id, Long organisationId) {
        log.debug("Recuperation de l'employe avec id: {}", id);
        EmployeModel entity = findByIdAndOrganisation(id, organisationId);
        return employeMapper.toResponse(entity);
    }

    /**
     * Liste tous les employes actifs d'une organisation.
     */
    @Transactional(readOnly = true)
    public List<EmployeResponse> getAll(Long organisationId) {
        log.debug("Recuperation de tous les employes pour l'organisation: {}", organisationId);
        List<EmployeModel> entities = employeRepository.findByOrganisationIdAndActifTrue(organisationId);
        return employeMapper.toResponseList(entities);
    }

    /**
     * Liste tous les employes actifs d'un hotel.
     */
    @Transactional(readOnly = true)
    public List<EmployeResponse> getAllByHotel(Long organisationId, Long hotelId) {
        log.debug("Recuperation de tous les employes pour l'hotel: {}", hotelId);
        List<EmployeModel> entities = employeRepository.findByOrganisationIdAndHotelIdAndActifTrue(organisationId, hotelId);
        return employeMapper.toResponseList(entities);
    }

    /**
     * Liste les employes avec pagination.
     */
    @Transactional(readOnly = true)
    public Page<EmployeResponse> getAllPaginated(Long organisationId, Pageable pageable) {
        log.debug("Recuperation paginee des employes pour l'organisation: {}", organisationId);
        Page<EmployeModel> entities = employeRepository.findByOrganisationIdAndActifTrue(organisationId, pageable);
        return entities.map(employeMapper::toResponse);
    }

    /**
     * Liste les employes d'un hotel avec pagination.
     */
    @Transactional(readOnly = true)
    public Page<EmployeResponse> getAllByHotelPaginated(Long organisationId, Long hotelId, Pageable pageable) {
        log.debug("Recuperation paginee des employes pour l'hotel: {}", hotelId);
        Page<EmployeModel> entities = employeRepository.findByOrganisationIdAndHotelIdAndActifTrue(organisationId, hotelId, pageable);
        return entities.map(employeMapper::toResponse);
    }

    /**
     * Liste les employes par departement.
     */
    @Transactional(readOnly = true)
    public List<EmployeResponse> getByDepartement(Long organisationId, String departement) {
        log.debug("Recuperation des employes du departement: {}", departement);
        List<EmployeModel> entities = employeRepository.findByOrganisationIdAndDepartementAndActifTrue(organisationId, departement);
        return employeMapper.toResponseList(entities);
    }

    /**
     * Supprime (soft delete) un employe.
     */
    public void delete(String uuid, Long organisationId, String username) {
        log.info("Suppression de l'employe avec uuid: {}", uuid);
        EmployeModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setSupprime(true);
        entity.setActif(false);
        entity.setModifiePar(username);
        employeRepository.save(entity);
        log.info("Employe supprime avec succes: id={}, uuid={}", entity.getId(), entity.getUuid());
    }

    /**
     * Active un employe.
     */
    public EmployeResponse activate(String uuid, Long organisationId, String username) {
        log.info("Activation de l'employe avec uuid: {}", uuid);
        EmployeModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setActif(true);
        entity.setSupprime(false);
        entity.setModifiePar(username);
        EmployeModel updated = employeRepository.save(entity);
        return employeMapper.toResponse(updated);
    }

    /**
     * Desactive un employe.
     */
    public EmployeResponse deactivate(String uuid, Long organisationId, String username) {
        log.info("Desactivation de l'employe avec uuid: {}", uuid);
        EmployeModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setActif(false);
        entity.setModifiePar(username);
        EmployeModel updated = employeRepository.save(entity);
        return employeMapper.toResponse(updated);
    }

    // Methodes privees

    private EmployeModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        return employeRepository.findByUuid(uuid)
                .filter(e -> e.getOrganisationId().equals(organisationId))
                .filter(e -> !Boolean.TRUE.equals(e.getSupprime()))
                .orElseThrow(() -> new ResourceNotFoundException("Employe", uuid));
    }

    private EmployeModel findByIdAndOrganisation(Long id, Long organisationId) {
        return employeRepository.findById(id)
                .filter(e -> e.getOrganisationId().equals(organisationId))
                .filter(e -> !Boolean.TRUE.equals(e.getSupprime()))
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
    }
}
