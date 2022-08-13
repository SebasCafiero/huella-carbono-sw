package ar.edu.utn.frba.dds.entities.lugares;

import ar.edu.utn.frba.dds.entities.EntidadPersistente;
import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Organizacion extends EntidadPersistente {
    private String razonSocial;
    private TipoDeOrganizacionEnum tipo;
    private UbicacionGeografica ubicacion;
    private ClasificacionOrganizacion clasificacionOrganizacion;
    private Set<Sector> sectores;
    private List<Medicion> mediciones;
    private List<String> contactosMail;
    private List<Integer> contactosTelefono;

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
        this.contactosMail = new ArrayList<>();
        this.contactosTelefono = new ArrayList<>();
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

    public void agregarMediciones(Medicion... variasMediciones) {
        Collections.addAll(this.mediciones, variasMediciones);
    }

    public List<Medicion> getMediciones() {
        return this.mediciones;
    }

    public Integer cantidadSectores() {
        return this.sectores.size();
    }

    public Float obtenerHC(LocalDate fechaInicial, LocalDate fechaFinal) {
        return 0.0f;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public TipoDeOrganizacionEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeOrganizacionEnum tipo) {this.tipo = tipo;}

    public void setClasificacionOrganizacion(ClasificacionOrganizacion clasificacionOrganizacion) {this.clasificacionOrganizacion = clasificacionOrganizacion;}

    public void setUbicacion(UbicacionGeografica ubicacion) {this.ubicacion = ubicacion;}

    public UbicacionGeografica getUbicacion() {
        return ubicacion;
    }

    public ClasificacionOrganizacion getClasificacionOrganizacion() {
        return clasificacionOrganizacion;
    }

    public Set<Sector> getSectores() {
        return sectores;
    }

    public static boolean existeTipoOrganizacion(String tipo) {
        try {
            TipoDeOrganizacionEnum tipoDeOrganizacion = TipoDeOrganizacionEnum.valueOf(tipo);
        } catch(IllegalArgumentException ex) {
            return false;
        }
        return true;
    }

    public List<String> getContactosMail() {
        return contactosMail;
    }

    public List<Integer> getContactosTelefono() {
        return contactosTelefono;
    }

    public void agregarContactoMail(String contacto) {
        this.contactosMail.add(contacto);
    }

    public void agregarContactoTelefono(Integer contacto) {
        this.contactosTelefono.add(contacto);
    }

    public List<Trayecto> trayectosDeMiembros() {
        return miembros().stream().flatMap(m -> m.getTrayectos().stream()).collect(Collectors.toList());
    }

    public Organizacion() {
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    @Override
    public String toString() {
        return "Organizacion{" +
                "razonSocial='" + razonSocial + '\'' +
                '}';
    }
}
