package projet_hotelier.hotel.module.finances.domain.event;

import lombok.Getter;
import lombok.ToString;
import projet_hotelier.hotel.shared.event.BaseDomainEvent;

import java.math.BigDecimal;

@Getter
@ToString(callSuper = true)
public class PaiementRecuEvent extends BaseDomainEvent {

    private final String codePaiement;
    private final String typePaiement;
    private final String modePaiement;
    private final BigDecimal montant;
    private final String devise;
    private final Long factureId;

    public PaiementRecuEvent(Long paiementId, Long organisationId, Long hotelId,
                             String codePaiement, String typePaiement, String modePaiement,
                             BigDecimal montant, String devise, Long factureId) {
        super(paiementId, "Paiement", organisationId, hotelId);
        this.codePaiement = codePaiement;
        this.typePaiement = typePaiement;
        this.modePaiement = modePaiement;
        this.montant = montant;
        this.devise = devise;
        this.factureId = factureId;
    }
}
