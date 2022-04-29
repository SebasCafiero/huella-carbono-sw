package ar.edu.utn.frba.dds.personas;

import ar.edu.utn.frba.dds.lugares.Organizacion;
import ar.edu.utn.frba.dds.lugares.Sector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Miembro {
    private String nombre;
    private String apellido;
    private TipoDeDocumento tipoDeDocumento;
    private int nroDocumento;
    private List<Sector> sectoresDondeTrabaja; //Los sectores conocen las organizaciones

    public Miembro(String nombre, String apellido, TipoDeDocumento tipoDeDocumento, int nroDocumento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDeDocumento = tipoDeDocumento;
        this.nroDocumento = nroDocumento;
        this.sectoresDondeTrabaja = new ArrayList<Sector>();
    }

    public List<Organizacion> organizacionesDondeTrabaja() {
        return sectoresDondeTrabaja
                .stream()
                .map(sector -> sector.getOrganizacion())
                .collect(Collectors.toList());
    }

    public void agregarSector(Sector sector) throws Exception{
        if(!this.sectoresDondeTrabaja.contains(sector))
            throw new Exception("El miembro ya pertenece a ese sector");
        this.sectoresDondeTrabaja.add(sector);
    }

    public void quitarSector(Sector sector) {
        this.sectoresDondeTrabaja.remove(sector);
    }

    //TODO
    //Ver si conviene que sea la lista de organizaciones donde trabaja el atributo, y obtener los sectores
    // buscando donde la persona sea miembro, o teniendo ambos atributos.
}

