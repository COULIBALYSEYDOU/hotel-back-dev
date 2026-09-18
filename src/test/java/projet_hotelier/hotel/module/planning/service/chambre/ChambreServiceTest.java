package projet_hotelier.hotel.module.planning.service.chambre;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projet_hotelier.hotel.module.planning.dto.request.chambre.CreateChambreRequest;
import projet_hotelier.hotel.module.planning.dto.response.chambre.ChambreResponse;
import projet_hotelier.hotel.module.planning.mapper.ChambreMapper;
import projet_hotelier.hotel.module.planning.model.chambre.ChambreModel;
import projet_hotelier.hotel.module.planning.repository.ChambreRepository;
import projet_hotelier.hotel.shared.exception.ConflictException;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ChambreService — règles métier")
class ChambreServiceTest {

    @Mock ChambreRepository chambreRepository;
    @Mock ChambreMapper chambreMapper;
    @InjectMocks ChambreService service;

    private static final Long ORG = 1L;

    private CreateChambreRequest req(String numero) {
        return CreateChambreRequest.builder()
                .numero(numero)
                .prixBase(new BigDecimal("100.00"))
                .typeChambre("STANDARD")
                .capacite(2)
                .build();
    }

    @Test
    @DisplayName("create() : numéro déjà existant → ConflictException")
    void create_numeroDoublon_conflict() {
        when(chambreRepository.existsByNumero("101")).thenReturn(true);
        assertThatThrownBy(() -> service.create(req("101"), ORG, 10L, "user"))
                .isInstanceOf(ConflictException.class);
        verify(chambreRepository, never()).save(any());
    }

    @Test
    @DisplayName("create() : positionne organisationId et actif=true")
    void create_setOrgAndActive() {
        when(chambreRepository.existsByNumero("102")).thenReturn(false);
        when(chambreMapper.toEntity(any(CreateChambreRequest.class))).thenReturn(new ChambreModel());
        when(chambreRepository.save(any(ChambreModel.class))).thenAnswer(inv -> inv.getArgument(0));
        when(chambreMapper.toResponse(any())).thenReturn(new ChambreResponse());

        service.create(req("102"), ORG, 10L, "alice");

        ArgumentCaptor<ChambreModel> cap = ArgumentCaptor.forClass(ChambreModel.class);
        verify(chambreRepository).save(cap.capture());
        assertThat(cap.getValue().getOrganisationId()).isEqualTo(ORG);
        assertThat(cap.getValue().getActif()).isTrue();
        assertThat(cap.getValue().getSupprime()).isFalse();
        assertThat(cap.getValue().getCreePar()).isEqualTo("alice");
    }

    @Test
    @DisplayName("setHorsService(true, motif) : positionne horsService=true et motif")
    void setHorsService_active() {
        String uuid = UUID.randomUUID().toString();
        ChambreModel entity = new ChambreModel();
        entity.setOrganisationId(ORG);
        entity.setUuid(uuid);
        when(chambreRepository.findByUuid(uuid)).thenReturn(Optional.of(entity));
        when(chambreRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(chambreMapper.toResponse(any())).thenReturn(new ChambreResponse());

        service.setHorsService(uuid, true, "Rénovation", ORG, "bob");

        ArgumentCaptor<ChambreModel> cap = ArgumentCaptor.forClass(ChambreModel.class);
        verify(chambreRepository).save(cap.capture());
        assertThat(cap.getValue().isHorsService()).isTrue();
        assertThat(cap.getValue().getMotifHorsService()).isEqualTo("Rénovation");
    }

    @Test
    @DisplayName("setHorsService(false, _) : réinitialise motif à null")
    void setHorsService_off_efface_motif() {
        String uuid = UUID.randomUUID().toString();
        ChambreModel entity = new ChambreModel();
        entity.setOrganisationId(ORG);
        entity.setUuid(uuid);
        entity.setHorsService(true);
        entity.setMotifHorsService("Ancien motif");
        when(chambreRepository.findByUuid(uuid)).thenReturn(Optional.of(entity));
        when(chambreRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(chambreMapper.toResponse(any())).thenReturn(new ChambreResponse());

        service.setHorsService(uuid, false, "peu importe", ORG, "bob");

        ArgumentCaptor<ChambreModel> cap = ArgumentCaptor.forClass(ChambreModel.class);
        verify(chambreRepository).save(cap.capture());
        assertThat(cap.getValue().isHorsService()).isFalse();
        assertThat(cap.getValue().getMotifHorsService()).isNull();
    }

    @Test
    @DisplayName("delete() : soft-delete (actif=false, supprime=true)")
    void delete_softDelete() {
        String uuid = UUID.randomUUID().toString();
        ChambreModel entity = new ChambreModel();
        entity.setOrganisationId(ORG);
        entity.setUuid(uuid);
        entity.setActif(true);
        when(chambreRepository.findByUuid(uuid)).thenReturn(Optional.of(entity));
        when(chambreRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        service.delete(uuid, ORG, "bob");

        ArgumentCaptor<ChambreModel> cap = ArgumentCaptor.forClass(ChambreModel.class);
        verify(chambreRepository).save(cap.capture());
        assertThat(cap.getValue().getActif()).isFalse();
        assertThat(cap.getValue().getSupprime()).isTrue();
    }

    @Test
    @DisplayName("getByUuid() : organisation différente → 404")
    void getByUuid_autreOrg_notFound() {
        String uuid = UUID.randomUUID().toString();
        ChambreModel entity = new ChambreModel();
        entity.setOrganisationId(999L);
        entity.setUuid(uuid);
        when(chambreRepository.findByUuid(uuid)).thenReturn(Optional.of(entity));

        assertThatThrownBy(() -> service.getByUuid(uuid, ORG))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
