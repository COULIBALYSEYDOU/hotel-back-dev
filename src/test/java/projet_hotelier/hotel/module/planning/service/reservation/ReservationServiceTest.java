package projet_hotelier.hotel.module.planning.service.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import projet_hotelier.hotel.module.planning.domain.event.ReservationConfirmeeEvent;
import projet_hotelier.hotel.module.planning.dto.request.reservation.CreateReservationRequest;
import projet_hotelier.hotel.module.planning.dto.response.reservation.ReservationResponse;
import projet_hotelier.hotel.module.planning.mapper.ReservationMapper;
import projet_hotelier.hotel.module.planning.model.reservation.ReservationModel;
import projet_hotelier.hotel.module.planning.repository.ReservationRepository;
import projet_hotelier.hotel.shared.exception.ConflictException;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;
import projet_hotelier.hotel.shared.exception.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReservationService — règles métier")
class ReservationServiceTest {

    @Mock ReservationRepository reservationRepository;
    @Mock ReservationMapper reservationMapper;
    @Mock ApplicationEventPublisher eventPublisher;

    @InjectMocks ReservationService service;

    private static final Long ORG = 1L;
    private static final Long HOTEL = 10L;
    private static final String USER = "alice";

    private CreateReservationRequest baseRequest() {
        return CreateReservationRequest.builder()
                .codeReservation("RES-001")
                .clientId(100L)
                .chambreId(200L)
                .dateArrivee(LocalDate.of(2026, 6, 1))
                .dateDepart(LocalDate.of(2026, 6, 5))
                .montantTotal(new BigDecimal("400.00"))
                .build();
    }

    private ReservationModel savedEntity(String uuid) {
        ReservationModel m = new ReservationModel();
        m.setId(1L);
        m.setUuid(uuid);
        m.setOrganisationId(ORG);
        m.setCodeReservation("RES-001");
        m.setClientId(100L);
        m.setChambreId(200L);
        m.setDateArrivee(LocalDate.of(2026, 6, 1));
        m.setDateDepart(LocalDate.of(2026, 6, 5));
        m.setMontantTotal(new BigDecimal("400.00"));
        m.setActif(true);
        m.setSupprime(false);
        return m;
    }

    @BeforeEach
    void setUp() {
        // mapper renvoie un ReservationModel neuf à chaque appel
        when(reservationMapper.toEntity(any(CreateReservationRequest.class)))
                .thenAnswer(inv -> {
                    CreateReservationRequest r = inv.getArgument(0);
                    ReservationModel m = new ReservationModel();
                    m.setCodeReservation(r.getCodeReservation());
                    m.setClientId(r.getClientId());
                    m.setChambreId(r.getChambreId());
                    m.setDateArrivee(r.getDateArrivee());
                    m.setDateDepart(r.getDateDepart());
                    m.setMontantTotal(r.getMontantTotal());
                    return m;
                });
        when(reservationMapper.toResponse(any(ReservationModel.class)))
                .thenAnswer(inv -> {
                    ReservationModel m = inv.getArgument(0);
                    return ReservationResponse.builder()
                            .id(m.getId())
                            .uuid(m.getUuid())
                            .codeReservation(m.getCodeReservation())
                            .statutReservation(m.getStatutReservation())
                            .build();
                });
    }

    @Test
    @DisplayName("create() : sans chevauchement, statut = EN_ATTENTE")
    void create_sansChevauchement_statutEnAttente() {
        CreateReservationRequest req = baseRequest();
        when(reservationRepository.existsByCodeReservation("RES-001")).thenReturn(false);
        when(reservationRepository.findOverlapping(eq(ORG), eq(200L), any(), any(), eq(null)))
                .thenReturn(Collections.emptyList());
        when(reservationRepository.save(any(ReservationModel.class))).thenAnswer(inv -> {
            ReservationModel m = inv.getArgument(0);
            m.setId(1L);
            m.setUuid(UUID.randomUUID().toString());
            return m;
        });

        ReservationResponse res = service.create(req, ORG, HOTEL, USER);

        ArgumentCaptor<ReservationModel> cap = ArgumentCaptor.forClass(ReservationModel.class);
        verify(reservationRepository).save(cap.capture());
        assertThat(cap.getValue().getStatutReservation()).isEqualTo(ReservationService.STATUT_EN_ATTENTE);
        assertThat(cap.getValue().getOrganisationId()).isEqualTo(ORG);
        assertThat(res).isNotNull();
    }

    @Test
    @DisplayName("create() : avec chevauchement, statut forcé à EN_CONFLIT (avertir mais autoriser)")
    void create_avecChevauchement_statutEnConflit() {
        CreateReservationRequest req = baseRequest();
        when(reservationRepository.existsByCodeReservation("RES-001")).thenReturn(false);
        when(reservationRepository.findOverlapping(eq(ORG), eq(200L), any(), any(), eq(null)))
                .thenReturn(List.of(new ReservationModel())); // au moins un conflit
        when(reservationRepository.save(any(ReservationModel.class))).thenAnswer(inv -> {
            ReservationModel m = inv.getArgument(0);
            m.setId(1L);
            m.setUuid(UUID.randomUUID().toString());
            return m;
        });

        service.create(req, ORG, HOTEL, USER);

        ArgumentCaptor<ReservationModel> cap = ArgumentCaptor.forClass(ReservationModel.class);
        verify(reservationRepository).save(cap.capture());
        assertThat(cap.getValue().getStatutReservation()).isEqualTo(ReservationService.STATUT_EN_CONFLIT);
    }

    @Test
    @DisplayName("create() : code réservation déjà utilisé → ConflictException")
    void create_codeDejaUtilise_conflict() {
        CreateReservationRequest req = baseRequest();
        when(reservationRepository.existsByCodeReservation("RES-001")).thenReturn(true);

        assertThatThrownBy(() -> service.create(req, ORG, HOTEL, USER))
                .isInstanceOf(ConflictException.class);

        verify(reservationRepository, never()).save(any());
    }

    @Test
    @DisplayName("create() : dateDepart <= dateArrivee → ValidationException")
    void create_datesInvalides_validationException() {
        CreateReservationRequest req = baseRequest();
        req.setDateDepart(req.getDateArrivee()); // depart == arrivee → invalide

        assertThatThrownBy(() -> service.create(req, ORG, HOTEL, USER))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    @DisplayName("isChambreDisponible() : renvoie true si aucun chevauchement")
    void isChambreDisponible_true() {
        when(reservationRepository.findOverlapping(eq(ORG), eq(200L), any(), any(), eq(null)))
                .thenReturn(Collections.emptyList());
        boolean dispo = service.isChambreDisponible(ORG, 200L, LocalDate.of(2026, 6, 1), LocalDate.of(2026, 6, 5));
        assertThat(dispo).isTrue();
    }

    @Test
    @DisplayName("isChambreDisponible() : renvoie false s'il y a chevauchement")
    void isChambreDisponible_false() {
        when(reservationRepository.findOverlapping(eq(ORG), eq(200L), any(), any(), eq(null)))
                .thenReturn(List.of(new ReservationModel()));
        boolean dispo = service.isChambreDisponible(ORG, 200L, LocalDate.of(2026, 6, 1), LocalDate.of(2026, 6, 5));
        assertThat(dispo).isFalse();
    }

    @Test
    @DisplayName("changerStatut(CONFIRMEE) : publie ReservationConfirmeeEvent")
    void changerStatut_confirmee_publieEvent() {
        String uuid = UUID.randomUUID().toString();
        ReservationModel entity = savedEntity(uuid);
        entity.setStatutReservation("EN_ATTENTE");
        when(reservationRepository.findByUuid(uuid)).thenReturn(Optional.of(entity));
        when(reservationRepository.save(any(ReservationModel.class))).thenAnswer(inv -> inv.getArgument(0));

        service.changerStatut(uuid, "CONFIRMEE", ORG, USER);

        ArgumentCaptor<ReservationConfirmeeEvent> cap = ArgumentCaptor.forClass(ReservationConfirmeeEvent.class);
        verify(eventPublisher, times(1)).publishEvent(cap.capture());
        assertThat(cap.getValue().getCodeReservation()).isEqualTo("RES-001");
        assertThat(cap.getValue().getMontantTotal()).isEqualByComparingTo(new BigDecimal("400.00"));
    }

    @Test
    @DisplayName("changerStatut(CONFIRMEE) : idempotent — pas de doublon si déjà CONFIRMEE")
    void changerStatut_confirmee_idempotent() {
        String uuid = UUID.randomUUID().toString();
        ReservationModel entity = savedEntity(uuid);
        entity.setStatutReservation("CONFIRMEE"); // déjà confirmé
        when(reservationRepository.findByUuid(uuid)).thenReturn(Optional.of(entity));
        when(reservationRepository.save(any(ReservationModel.class))).thenAnswer(inv -> inv.getArgument(0));

        service.changerStatut(uuid, "CONFIRMEE", ORG, USER);

        verify(eventPublisher, never()).publishEvent(any(ReservationConfirmeeEvent.class));
    }

    @Test
    @DisplayName("getByUuid() : ressource dans une autre organisation → 404")
    void getByUuid_mauvaiseOrg_notFound() {
        String uuid = UUID.randomUUID().toString();
        ReservationModel entity = savedEntity(uuid);
        entity.setOrganisationId(999L); // autre org
        when(reservationRepository.findByUuid(uuid)).thenReturn(Optional.of(entity));

        assertThatThrownBy(() -> service.getByUuid(uuid, ORG))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
