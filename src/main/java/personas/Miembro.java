package personas;

import lugares.Organizacion;
import lugares.Sector;

import java.util.List;
import java.util.stream.Collectors;

public class Miembro extends Persona{
    private List<Sector> sectoresDondeTrabaja; //Los sectores conocen las organizaciones

    public List<Organizacion> organizacionesDondeTrabaja() {
        return sectoresDondeTrabaja
                .stream()
                .map(sector -> sector.getOrganizacionAlQueCorresponde())
                .collect(Collectors.toList());
    }

    //TODO
    //Ver si conviene que sea la lista de organizaciones donde trabaja el atributo, y obtener los sectores
    // buscando donde la persona sea miembro, o teniendo ambos atributos.
}

