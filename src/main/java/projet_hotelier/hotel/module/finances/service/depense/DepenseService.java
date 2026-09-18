package projet_hotelier.hotel.module.finances.service.depense;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.finances.dto.request.depense.CreateDepenseRequest;
import projet_hotelier.hotel.module.finances.dto.response.depense.DepenseResponse;
import projet_hotelier.hotel.module.finances.mapper.DepenseMapper;
import projet_hotelier.hotel.module.finances.model.revenuDepensePaiement.DepenseModel;
import projet_hotelier.hotel.module.finances.repository.DepenseRepository;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DepenseService {

    private final DepenseRepository depenseRepository;
    private final DepenseMapper depenseMapper;

    public DepenseResponse create(CreateDepenseRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation depense libelle={} montantHT={}", request.getLibelle(), request.getMontantHT());
        DepenseModel entity = depenseMapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId != null ? hotelId : request.getHotelId());
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);
        entity.setCodeDepense("DEP-" + LocalDate.now().getYear() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        BigDecimal ht = entity.getMontantHT() != null ? entity.getMontantHT() : BigDecimal.ZERO;
        BigDecimal tauxTva = entity.getTauxTVA() != null ? entity.getTauxTVA() : BigDecimal.ZERO;
        BigDecimal tva = ht.multiply(tauxTva).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        entity.setMontantTVA(tva);
        entity.setMontantTTC(ht.add(tva));
        entity.setMontantPaye(BigDecimal.ZERO);
        entity.setMontantRestant(entity.getMontantTTC());
        entity.setStatutDepense("ENREGISTREE");
        entity.setValidee(false);
        entity.setPayee(false);
        return depenseMapper.toResponse(depenseRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public DepenseResponse getByUuid(String uuid, Long organisationId) {
        return depenseMapper.toResponse(findByUuidAndOrganisation(uuid, organisationId));
    }

    @Transactional(readOnly = true)
    public Page<DepenseResponse> getAllPaginated(Long organisationId, Pageable pageable) {
        return depenseRepository.findByOrganisationIdAndActifTrue(organisationId, pageable)
                .map(depenseMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<DepenseResponse> getByPeriode(Long organisationId, LocalDate dateDebut, LocalDate dateFin) {
        return depenseMapper.toResponseList(
                depenseRepository.findByOrganisationAndPeriode(organisationId, dateDebut, dateFin));
    }

    @Transactional(readOnly = true)
    public List<DepenseResponse> getPendingValidation(Long organisationId) {
        return depenseMapper.toResponseList(depenseRepository.findPendingValidation(organisationId));
    }

    public DepenseResponse valider(String uuid, Long organisationId, String username) {
        DepenseModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setValidee(true);
        entity.setStatutDepense("VALIDEE");
        entity.setModifiePar(username);
        return depenseMapper.toResponse(depenseRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public BigDecimal sumByPeriode(Long organisationId, LocalDate dateDebut, LocalDate dateFin) {
        BigDecimal s = depenseRepository.sumDepensesByPeriode(organisationId, dateDebut, dateFin);
        return s != null ? s : BigDecimal.ZERO;
    }

    public void delete(String uuid, Long organisationId, String username) {
        DepenseModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setActif(false);
        entity.setSupprime(true);
        entity.setModifiePar(username);
        depenseRepository.save(entity);
    }

    private DepenseModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        DepenseModel entity = depenseRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Depense", "uuid", uuid));
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("Depense", "uuid", uuid);
        }
        return entity;
    }
}
