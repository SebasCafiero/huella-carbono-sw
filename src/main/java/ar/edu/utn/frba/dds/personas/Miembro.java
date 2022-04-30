package ar.edu.utn.frba.dds.personas;

import ar.edu.utn.frba.dds.lugares.Organizacion;
import ar.edu.utn.frba.dds.lugares.Sector;

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
        if(this.sectoresDondeTrabaja.contains(sector))
            throw new Exception("El miembro ya pertenece a ese sector");
        this.sectoresDondeTrabaja.add(sector);
    }

    public void quitarSector(Sector sector) throws Exception {
        if(!this.sectoresDondeTrabaja.contains(sector))
            throw new Exception("El miembro no pertenece a ese sector");
        this.sectoresDondeTrabaja.remove(sector);
    }

    public void solicitarIngreso(Sector sector) {
        //TODO
        //Tendría que agregarse a la lista de postulantes del sector y
        // cuando lo agreguen, sumar el sector a sectoresDondeTrabaja
    }

}

