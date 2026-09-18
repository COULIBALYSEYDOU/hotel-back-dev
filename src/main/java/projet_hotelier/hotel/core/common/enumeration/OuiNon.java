package projet_hotelier.hotel.core.common.enumeration;

public enum OuiNon {
    OUI,
    NON;

    public boolean isOui() {
        return this == OUI;
    }

    public boolean isNon() {
        return this == NON;
    }
}

