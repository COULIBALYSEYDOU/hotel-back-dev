package projet_hotelier.hotel.module.finances.domain.event;

import lombok.Getter;
import lombok.ToString;
import projet_hotelier.hotel.shared.event.BaseDomainEvent;

import java.math.BigDecimal;

@Getter
@ToString(callSuper = true)
public class FactureEmiseEvent extends BaseDomainEvent {

    private final String numeroFacture;
    private final String typeFacture;
    private final Long clientId;
    private final String clientNom;
    private final BigDecimal montantTTC;
    private final String devise;

    public FactureEmiseEvent(Long factureId, Long organisationId, Long hotelId,
                             String numeroFacture, String typeFacture,
                             Long clientId, String clientNom,
                             BigDecimal montantTTC, String devise) {
        super(factureId, "Facture", organisationId, hotelId);
        this.numeroFacture = numeroFacture;
        this.typeFacture = typeFacture;
        this.clientId = clientId;
        this.clientNom = clientNom;
        this.montantTTC = montantTTC;
        this.devise = devise;
    }
}
