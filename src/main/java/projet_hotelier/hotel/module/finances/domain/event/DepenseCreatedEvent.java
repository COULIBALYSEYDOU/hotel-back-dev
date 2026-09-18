package projet_hotelier.hotel.module.finances.domain.event;

import lombok.Getter;
import lombok.ToString;
import projet_hotelier.hotel.shared.event.BaseDomainEvent;

import java.math.BigDecimal;

@Getter
@ToString(callSuper = true)
public class DepenseCreatedEvent extends BaseDomainEvent {

    private final String codeDepense;
    private final String libelle;
    private final BigDecimal montantTTC;
    private final String devise;
    private final Long fournisseurId;

    public DepenseCreatedEvent(Long depenseId, Long organisationId, Long hotelId,
                               String codeDepense, String libelle, BigDecimal montantTTC,
                               String devise, Long fournisseurId) {
        super(depenseId, "Depense", organisationId, hotelId);
        this.codeDepense = codeDepense;
        this.libelle = libelle;
        this.montantTTC = montantTTC;
        this.devise = devise;
        this.fournisseurId = fournisseurId;
    }
}
