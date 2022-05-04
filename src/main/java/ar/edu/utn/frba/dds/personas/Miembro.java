package ar.edu.utn.frba.dds.personas;

import ar.edu.utn.frba.dds.lugares.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Miembro {
    private String nombre;
    private String apellido;
    private TipoDeDocumento tipoDeDocumento;
    private int nroDocumento;
    private Set<Sector> sectoresDondeTrabaja; //Los sectores conocen las organizaciones

    public Miembro(String nombre, String apellido, TipoDeDocumento tipoDeDocumento, int nroDocumento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDeDocumento = tipoDeDocumento;
        this.nroDocumento = nroDocumento;
        this.sectoresDondeTrabaja = new HashSet<>();
    }

    public Set<Sector> getSectoresDondeTrabaja() {
        return this.sectoresDondeTrabaja;
    }

    public Set<Organizacion> organizacionesDondeTrabaja() { //TODO DEBERIA SER DIRECTAMENTE ATRIBUTO DEL MIEMBRO?
        return this.sectoresDondeTrabaja
                .stream()
                .map(sector -> sector.getOrganizacion())
                .collect(Collectors.toSet());
    }

    public String nombreCompleto() {
        return this.nombre + " " + this.apellido;
    }

    public void agregarSector(Sector sector) throws Exception {
        if(this.trabajaEnSector(sector))
            throw new Exception("El miembro ya pertenece a ese sector");
        this.sectoresDondeTrabaja.add(sector);
    }

    public void quitarSector(Sector sector) throws Exception {
        if(!this.trabajaEnSector(sector))
            throw new Exception("El miembro no pertenece a ese sector");
        this.sectoresDondeTrabaja.remove(sector);
    }

    public void solicitarIngreso(Sector sector) throws Exception {
        //TODO
        //Tendría que agregarse a la lista de postulantes del sector y
        // cuando lo agreguen, sumar el sector a sectoresDondeTrabaja
        if(sector.esMiembro(this))
            throw new Exception("El miembro ya pertenece a la organizacion");
        sector.agregarPostulante(this);
    }

    public Integer cantidadDeSectoresDondeTrabaja() {
        return this.sectoresDondeTrabaja.size();
    }

    public Integer cantidadDeOrganizacionesDondeTrabaja() {
        return this.organizacionesDondeTrabaja().size();
    }

    public boolean trabajaEnSector(Sector unSector) {
        return this.sectoresDondeTrabaja.contains(unSector);
    }

    public boolean trabajaEnTipoDeOrganizacion(TipoDeOrganizacionEnum tipo) {
        return this.organizacionesDondeTrabaja()
                .stream()
                .map(unaOrg -> unaOrg.getTipo())
                .collect(Collectors.toSet())
                .contains(tipo);
    }

    public Integer cantidadDeTipoDeOrg(TipoDeOrganizacionEnum tipo) {
        return this.organizacionesDondeTrabaja()
                //.stream()
                //.map(unaOrg -> unaOrg.getTipo())
                //.collect(Collectors.toSet())
                .size();
    }
}

