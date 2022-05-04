package ar.edu.utn.frba.dds.lugares;

import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import ar.edu.utn.frba.dds.personas.Miembro;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Organizacion {

    protected String razonSocial;
    protected TipoDeOrganizacionEnum tipo;
    protected String ubicacion;
    protected ClasificacionOrganizacion clasificacionOrganizacion;
    protected Set<Sector> sectores;
    protected List<Medible> mediciones;


    // Para qué sirve ??
    public Set<Miembro> obtenerMiembrosDeLaOrganizacion(){
        //Hay que traer cada miembro de cada sector y que no hayan repetidos.
        return sectores
                .stream()
                .flatMap(sector -> sector.getListaDeMiembros().stream())
                .collect(Collectors.toSet());
    }

    public void agregarSector(Sector sector) throws Exception{
        if(this.sectores.contains(sector)) {
            throw new Exception("El sector ya pertenece a la organización.");
        }
        sectores.add(sector);
    }

    //Para que shallow copy?
    public List<Sector> getListaDeSectores() {
        List<Sector> shallowCopy = new ArrayList<Sector>();
        shallowCopy.addAll(this.sectores);
        return shallowCopy;
    }

    public void aceptarSolicitud(Miembro miembro, Sector sector) throws Exception {
        if(!this.sectores.contains(sector))
            throw new Exception("El sector no pertenece a la organizacion");
        for(Sector unSector : this.sectores) {
            if (unSector.esMiembro(miembro)) {
                throw new Exception("El miembro ya pertenece a la organizacion");
            }
        }
        sector.agregarMiembro(miembro);
        miembro.agregarSector(sector);
        sector.quitarPostulante(miembro);
    }

    public void rechazarSolicitud(Miembro miembro, Sector sector) throws Exception {
        //TODO
        sector.quitarPostulante(miembro);
    }

    public void agregarMediciones(Medible ... variasMediciones) {
        Collections.addAll(this.mediciones, variasMediciones);
    }

    public List<Medible> getMediciones() {
        return this.mediciones;
    }

    public Float obtenerHC(LocalDate fechaInicial, LocalDate fechaFinal) {
        return 0.0f;
    }

    public Integer cantidadSectores() {
        return this.sectores.size();
    }

    public TipoDeOrganizacionEnum getTipo() {
        return tipo;
    }
}