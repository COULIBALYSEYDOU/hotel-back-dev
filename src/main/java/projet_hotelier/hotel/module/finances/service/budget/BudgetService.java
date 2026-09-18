package projet_hotelier.hotel.module.finances.service.budget;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.finances.dto.request.budget.CreateBudgetRequest;
import projet_hotelier.hotel.module.finances.dto.request.budget.UpdateBudgetRequest;
import projet_hotelier.hotel.module.finances.dto.response.budget.BudgetResponse;
import projet_hotelier.hotel.module.finances.mapper.BudgetMapper;
import projet_hotelier.hotel.module.finances.model.budget.BudgetModel;
import projet_hotelier.hotel.module.finances.repository.BudgetRepository;
import projet_hotelier.hotel.shared.exception.ConflictException;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;

    public BudgetResponse create(CreateBudgetRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation budget code={}", request.getCodeBudget());
        if (budgetRepository.existsByCodeBudget(request.getCodeBudget())) {
            throw ConflictException.duplicate("Budget", "codeBudget", request.getCodeBudget());
        }
        BudgetModel entity = budgetMapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);
        if (entity.getMontantTotalReel() == null) entity.setMontantTotalReel(BigDecimal.ZERO);
        recalculerEcarts(entity);
        entity.setStatutBudget("BROUILLON");
        entity.setSoumis(false);
        entity.setApprouve(false);
        return budgetMapper.toResponse(budgetRepository.save(entity));
    }

    public BudgetResponse update(String uuid, UpdateBudgetRequest request, Long organisationId, String username) {
        BudgetModel entity = findByUuidAndOrganisation(uuid, organisationId);
        budgetMapper.updateEntity(entity, request);
        entity.setModifiePar(username);
        recalculerEcarts(entity);
        return budgetMapper.toResponse(budgetRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public BudgetResponse getByUuid(String uuid, Long organisationId) {
        return budgetMapper.toResponse(findByUuidAndOrganisation(uuid, organisationId));
    }

    @Transactional(readOnly = true)
    public BudgetResponse getByCode(String code, Long organisationId) {
        BudgetModel entity = budgetRepository.findByCodeBudget(code)
                .orElseThrow(() -> new ResourceNotFoundException("Budget", "codeBudget", code));
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("Budget", "codeBudget", code);
        }
        return budgetMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<BudgetResponse> getAll(Long organisationId) {
        return budgetMapper.toResponseList(budgetRepository.findByOrganisationIdAndActifTrue(organisationId));
    }

    @Transactional(readOnly = true)
    public Page<BudgetResponse> getAllPaginated(Long organisationId, Pageable pageable) {
        return budgetRepository.findByOrganisationIdAndActifTrue(organisationId, pageable)
                .map(budgetMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<BudgetResponse> getActifs(Long organisationId) {
        return budgetMapper.toResponseList(
                budgetRepository.findActiveByOrganisationAndDate(organisationId, LocalDate.now()));
    }

    @Transactional(readOnly = true)
    public List<BudgetResponse> getPendingApproval(Long organisationId) {
        return budgetMapper.toResponseList(budgetRepository.findPendingApproval(organisationId));
    }

    public BudgetResponse soumettre(String uuid, Long organisationId, String username) {
        BudgetModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setSoumis(true);
        entity.setDateSoumission(LocalDateTime.now());
        entity.setStatutBudget("SOUMIS");
        entity.setModifiePar(username);
        return budgetMapper.toResponse(budgetRepository.save(entity));
    }

    public BudgetResponse approuver(String uuid, Long organisationId, String username) {
        BudgetModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setApprouve(true);
        entity.setDateApprobation(LocalDateTime.now());
        entity.setStatutBudget("APPROUVE");
        entity.setModifiePar(username);
        return budgetMapper.toResponse(budgetRepository.save(entity));
    }

    public void delete(String uuid, Long organisationId, String username) {
        BudgetModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setActif(false);
        entity.setSupprime(true);
        entity.setModifiePar(username);
        budgetRepository.save(entity);
    }

    private void recalculerEcarts(BudgetModel entity) {
        BigDecimal prev = entity.getMontantTotalPrev() != null ? entity.getMontantTotalPrev() : BigDecimal.ZERO;
        BigDecimal reel = entity.getMontantTotalReel() != null ? entity.getMontantTotalReel() : BigDecimal.ZERO;
        entity.setMontantTotalEcart(reel.subtract(prev));
        entity.setMontantTotalReste(prev.subtract(reel));
    }

    private BudgetModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        BudgetModel entity = budgetRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Budget", "uuid", uuid));
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("Budget", "uuid", uuid);
        }
        return entity;
    }
}
