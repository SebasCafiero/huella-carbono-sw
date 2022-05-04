package ar.edu.utn.frba.dds.lugares;

import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import ar.edu.utn.frba.dds.personas.Miembro;
import ar.edu.utn.frba.dds.mediciones.Medicion;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Organizacion {
    private String razonSocial;
    private TipoDeOrganizacionEnum tipo;

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    private String ubicacion;
    private ClasificacionOrganizacion clasificacionOrganizacion;
    private Set<Sector> sectores;
    private List<Medicion> mediciones;

    public Organizacion(String razonSocial,
                        TipoDeOrganizacionEnum tipo,
                        ClasificacionOrganizacion clasificacionOrganizacion,
                        String ubicacion) {
        this.razonSocial = razonSocial;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.clasificacionOrganizacion = clasificacionOrganizacion;
        this.sectores = new HashSet<>();
        this.mediciones = new ArrayList<>();
    }

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
        //TODO
        sector.agregarMiembro(miembro); //CHECK SI ESTA BIEN QUE LAS EXCEPTIONS ESTEN EN AMBOS LADOS
        miembro.agregarSector(sector);
        sector.quitarPostulante(miembro);
        //SI aceptarSolicitud y rechazarSolicitud estuvieran directo en la clase Sector,
        // la Org no sería la encargada de aceptar los vínculos como dice el enunciado
    }

    public void rechazarSolicitud(Miembro miembro, Sector sector) throws Exception {
        //TODO
        sector.quitarPostulante(miembro);
    }

    public void agregarMediciones(Medicion ... variasMediciones) {
        Collections.addAll(this.mediciones, variasMediciones);
    }

    public List<Medicion> getMediciones() {
        return this.mediciones;
    }

    public Float obtenerHC(LocalDate fechaInicial, LocalDate fechaFinal) {
        return 0.0f;
    }

    public Integer cantidadSectores() {
        return this.sectores.size();
    }
}
