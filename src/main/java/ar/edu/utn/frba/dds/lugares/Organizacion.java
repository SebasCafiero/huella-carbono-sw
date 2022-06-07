package ar.edu.utn.frba.dds.lugares;

import ar.edu.utn.frba.dds.excepciones.MiembroException;
import ar.edu.utn.frba.dds.excepciones.SectorException;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import ar.edu.utn.frba.dds.personas.Miembro;
import ar.edu.utn.frba.dds.trayectos.Trayecto;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Organizacion {
    private String razonSocial;
    private TipoDeOrganizacionEnum tipo;
    private UbicacionGeografica ubicacion;
    private ClasificacionOrganizacion clasificacionOrganizacion;
    private Set<Sector> sectores;
    private List<Medible> mediciones;
    private Map<Miembro,Trayecto> trayectos; //TODO lo deberia tener organizacion o sector?

    public Organizacion(String razonSocial,
                        TipoDeOrganizacionEnum tipo,
                        ClasificacionOrganizacion clasificacionOrganizacion,
                        UbicacionGeografica ubicacion) {
        this.razonSocial = razonSocial;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.clasificacionOrganizacion = clasificacionOrganizacion;
        this.sectores = new HashSet<>();
        this.mediciones = new ArrayList<>();
        this.trayectos = new HashMap<Miembro,Trayecto>();
    }

    public Set<Miembro> miembros() {
        //Hay que traer cada miembro de cada sector y que no hayan repetidos.
        return sectores
                .stream()
                .flatMap(sector -> sector.getListaDeMiembros().stream())
                .collect(Collectors.toSet());
    }

    public void agregarSector(Sector sector) throws SectorException {
        if (this.sectores.contains(sector)) {
            throw new SectorException("El sector ya pertenece a la organización.");
        }
        sectores.add(sector);
    }


    public HashSet<Sector> getListaDeSectores() {
        return (HashSet<Sector>) this.sectores;
    }


    public void agregarMediciones(Medible... variasMediciones) {
        Collections.addAll(this.mediciones, variasMediciones);
    }

    public List<Medible> getMediciones() {
        return this.mediciones;
    }

    public Integer cantidadSectores() {
        return this.sectores.size();
    }

    public Float obtenerHC(LocalDate fechaInicial, LocalDate fechaFinal) {
        return 0.0f;
    }

    public void cargarTrayecto(Trayecto unTrayecto, Miembro unMiembro) {
        this.trayectos.put(unMiembro,unTrayecto);
    }

    public Float obtenerDistanciaTrayecto() {
        //PARA PRUEBITA, FALTA ARREGLAR TODO
        List<Trayecto> listaTrayectos = new ArrayList<Trayecto>(this.trayectos.values());
        return listaTrayectos.get(0).calcularDistancia();
    }

}
