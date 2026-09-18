package projet_hotelier.hotel.core.common;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * SearchFilter
 */
@Embeddable
@Getter
@Setter
@ToString
public class SearchFilter {
    private String keyword;
    private Boolean exactMatch = false;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    private LocalDateTime dateHeureDebut;
    private LocalDateTime dateHeureFin;

    private Integer page = 0;
    private Integer size = 20;

    private Boolean paginationActive = true;

    private String sortBy;
    private String sortDirection;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> statuts;

    private Boolean actif;

    private Long organisationId;
    private Long hotelId;
    private Long siteId;

    private String pays;
    private String region;
    private String ville;
    private String zoneGeo;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> idsInclus;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> idsExclus;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, String> customFilters;

    private String langue;        // fr, en
    private String fuseauHoraire; // Africa/Abidjan
    private String device;        // mobile, web
    private String origine;       // UI, API, BACKOFFICE
}

