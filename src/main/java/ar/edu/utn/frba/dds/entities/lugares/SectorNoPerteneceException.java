package ar.edu.utn.frba.dds.entities.lugares;

public class SectorNoPerteneceException extends Throwable {
    private final Sector sector;
    public SectorNoPerteneceException(Sector sector) {
        this.sector = sector;
    }

    public Sector getSector() {
        return sector;
    }
}
