package projet_hotelier.hotel.module.finances.service.revenu;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.finances.dto.request.revenu.CreateRevenuRequest;
import projet_hotelier.hotel.module.finances.dto.response.revenu.RevenuResponse;
import projet_hotelier.hotel.module.finances.mapper.RevenuMapper;
import projet_hotelier.hotel.module.finances.model.revenuDepensePaiement.RevenuModel;
import projet_hotelier.hotel.module.finances.repository.RevenuRepository;
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
public class RevenuService {

    private final RevenuRepository revenuRepository;
    private final RevenuMapper revenuMapper;

    public RevenuResponse create(CreateRevenuRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation revenu libelle={} montantHT={}", request.getLibelle(), request.getMontantHT());
        RevenuModel entity = revenuMapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId != null ? hotelId : request.getHotelId());
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);
        entity.setCodeRevenu("REV-" + LocalDate.now().getYear() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        // Calcul TVA/TTC
        BigDecimal ht = entity.getMontantHT() != null ? entity.getMontantHT() : BigDecimal.ZERO;
        BigDecimal tauxTva = entity.getTauxTVA() != null ? entity.getTauxTVA() : BigDecimal.ZERO;
        BigDecimal tva = ht.multiply(tauxTva).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        entity.setMontantTVA(tva);
        entity.setMontantTTC(ht.add(tva));
        entity.setMontantEncaisse(BigDecimal.ZERO);
        entity.setMontantRestant(entity.getMontantTTC());
        entity.setStatutRevenu("ENREGISTRE");
        return revenuMapper.toResponse(revenuRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public RevenuResponse getByUuid(String uuid, Long organisationId) {
        return revenuMapper.toResponse(findByUuidAndOrganisation(uuid, organisationId));
    }

    @Transactional(readOnly = true)
    public Page<RevenuResponse> getAllPaginated(Long organisationId, Pageable pageable) {
        return revenuRepository.findByOrganisationIdAndActifTrue(organisationId, pageable)
                .map(revenuMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<RevenuResponse> getByPeriode(Long organisationId, LocalDate dateDebut, LocalDate dateFin) {
        return revenuMapper.toResponseList(
                revenuRepository.findByOrganisationAndPeriode(organisationId, dateDebut, dateFin));
    }

    @Transactional(readOnly = true)
    public BigDecimal sumByPeriode(Long organisationId, LocalDate dateDebut, LocalDate dateFin) {
        BigDecimal s = revenuRepository.sumRevenusByPeriode(organisationId, dateDebut, dateFin);
        return s != null ? s : BigDecimal.ZERO;
    }

    public void delete(String uuid, Long organisationId, String username) {
        RevenuModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setActif(false);
        entity.setSupprime(true);
        entity.setModifiePar(username);
        revenuRepository.save(entity);
    }

    private RevenuModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        RevenuModel entity = revenuRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Revenu", "uuid", uuid));
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("Revenu", "uuid", uuid);
        }
        return entity;
    }
}
