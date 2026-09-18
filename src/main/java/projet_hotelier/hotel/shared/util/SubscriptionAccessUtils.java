package projet_hotelier.hotel.shared.util;

import projet_hotelier.hotel.core.config.FeatureToggleModel;
import projet_hotelier.hotel.core.config.HotelConfigModel;
import projet_hotelier.hotel.core.organisation.OrganisationSaaS;
import projet_hotelier.hotel.core.organisation.PlanSaaS;
import projet_hotelier.hotel.core.organisation.enumeration.ModuleSaaSType;

import java.util.Set;

public final class SubscriptionAccessUtils {

    private SubscriptionAccessUtils() {
    }

    public static boolean moduleAccessible(
            String moduleCode,
            HotelConfigModel hotelConfig,
            PlanSaaS planSaaS,
            OrganisationSaaS organisation
    ) {
        if (moduleCode == null || moduleCode.isBlank()) return false;
        if (hotelConfig != null && !hotelConfig.estExploitable()) return false;
        if (hotelConfig != null && !hotelConfig.moduleActif(moduleCode)) return false;

        if (hotelConfig != null && hotelConfig.isLimitationParPlan() && planSaaS != null) {
            if (!planAutoriseModule(planSaaS, moduleCode)) return false;
        }

        if (organisation != null && !organisationAutoriseModule(organisation, moduleCode)) {
            return false;
        }

        return true;
    }

    public static boolean featureAccessible(
            String featureCode,
            HotelConfigModel hotelConfig,
            PlanSaaS planSaaS,
            FeatureToggleModel featureToggle
    ) {
        if (featureCode == null || featureCode.isBlank()) return false;
        if (hotelConfig != null && !hotelConfig.estExploitable()) return false;
        if (hotelConfig != null && !hotelConfig.featureActive(featureCode)) return false;

        if (hotelConfig != null && hotelConfig.isLimitationParPlan() && planSaaS != null) {
            if (!planAutoriseFeature(planSaaS, featureCode)) return false;
        }

        if (featureToggle != null) {
            if (!featureToggle.estActif()) return false;
            if (hotelConfig != null
                    && !featureToggle.estApplicable(
                    hotelConfig.getOrganisationId(),
                    hotelConfig.getHotelId())
            ) {
                return false;
            }
        }

        return true;
    }

    public static boolean planAutoriseModule(PlanSaaS planSaaS, String moduleCode) {
        if (planSaaS == null || moduleCode == null) return true;
        ModuleSaaSType type = parseModuleType(moduleCode);
        Set<ModuleSaaSType> inclus = planSaaS.getModulesInclus();
        Set<ModuleSaaSType> optionnels = planSaaS.getModulesOptionnels();
        if (type == null) return inclus == null && optionnels == null;
        if (inclus != null && inclus.contains(type)) return true;
        return optionnels != null && optionnels.contains(type);
    }

    public static boolean planAutoriseFeature(PlanSaaS planSaaS, String featureCode) {
        if (planSaaS == null || featureCode == null) return true;
        Set<String> inclus = planSaaS.getFeaturesInclus();
        Set<String> optionnels = planSaaS.getFeaturesOptionnelles();
        if (inclus != null && inclus.contains(featureCode)) return true;
        return optionnels != null && optionnels.contains(featureCode);
    }

    private static boolean organisationAutoriseModule(OrganisationSaaS organisation, String moduleCode) {
        String code = moduleCode.trim().toUpperCase();
        return switch (code) {
            case "RH" -> Boolean.TRUE.equals(organisation.getModuleRHActive());
            case "FINANCES", "FINANCE" -> Boolean.TRUE.equals(organisation.getModuleFacturationActive());
            case "CLIENTELE", "CRM" -> Boolean.TRUE.equals(organisation.getModuleCRMActive());
            case "PLANNING", "RESERVATION" -> Boolean.TRUE.equals(organisation.getModuleReservationActive());
            case "REPORTING" -> Boolean.TRUE.equals(organisation.getModuleRapportActive());
            default -> true;
        };
    }

    private static ModuleSaaSType parseModuleType(String moduleCode) {
        try {
            return ModuleSaaSType.valueOf(moduleCode.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
