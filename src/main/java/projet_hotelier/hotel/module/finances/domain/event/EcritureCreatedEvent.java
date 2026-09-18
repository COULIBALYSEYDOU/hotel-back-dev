package projet_hotelier.hotel.module.finances.domain.event;

import lombok.Getter;
import lombok.ToString;
import projet_hotelier.hotel.shared.event.BaseDomainEvent;

import java.math.BigDecimal;

@Getter
@ToString(callSuper = true)
public class EcritureCreatedEvent extends BaseDomainEvent {

    private final String numeroEcriture;
    private final String codeJournal;
    private final String typeEcriture;
    private final BigDecimal totalDebit;
    private final BigDecimal totalCredit;
    private final String devise;

    public EcritureCreatedEvent(Long ecritureId, Long organisationId, Long hotelId,
                                String numeroEcriture, String codeJournal, String typeEcriture,
                                BigDecimal totalDebit, BigDecimal totalCredit, String devise) {
        super(ecritureId, "EcritureComptable", organisationId, hotelId);
        this.numeroEcriture = numeroEcriture;
        this.codeJournal = codeJournal;
        this.typeEcriture = typeEcriture;
        this.totalDebit = totalDebit;
        this.totalCredit = totalCredit;
        this.devise = devise;
    }
}
