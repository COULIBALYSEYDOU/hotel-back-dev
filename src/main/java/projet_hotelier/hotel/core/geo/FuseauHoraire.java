package projet_hotelier.hotel.core.geo;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import java.time.ZoneId;

@Entity
@Table(name = "fuseaux_horaires")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class FuseauHoraire extends BaseEntity {

    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code; // ex: "UTC+0", "Africa/Abidjan"

    @Column(name = "label", nullable = false, length = 100)
    private String label; // ex: "Africa/Abidjan"

    @Column(name = "zone_id", nullable = false, unique = true, length = 100)
    private String zoneId; // ZoneId Java standard

    public FuseauHoraire(String zoneId) {
        if (!ZoneId.getAvailableZoneIds().contains(zoneId)) {
            throw new IllegalArgumentException("Fuseau horaire invalide : " + zoneId);
        }
        this.zoneId = zoneId;
        this.code = zoneId;
        this.label = zoneId;
    }

    public ZoneId toZoneId() {
        return ZoneId.of(zoneId);
    }
}
