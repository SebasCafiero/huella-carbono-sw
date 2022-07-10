package ar.edu.utn.frba.dds.personas;

import ar.edu.utn.frba.dds.excepciones.MiembroException;
import ar.edu.utn.frba.dds.lugares.Organizacion;
import ar.edu.utn.frba.dds.lugares.Sector;
import ar.edu.utn.frba.dds.lugares.UbicacionGeografica;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import ar.edu.utn.frba.dds.trayectos.Trayecto;

import java.util.*;
import java.util.stream.Collectors;

public class Miembro {
    private String nombre;
    private String apellido;
    private TipoDeDocumento tipoDeDocumento;
    private int nroDocumento;
    private Set<Sector> sectoresDondeTrabaja; //Los sectores conocen las organizaciones
    private List<Trayecto> trayectos;
    private UbicacionGeografica domicilio; //Podria tener varios domicilios?

    public Miembro(String nombre, String apellido, TipoDeDocumento tipoDeDocumento, int nroDocumento, UbicacionGeografica hogar) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDeDocumento = tipoDeDocumento;
        this.nroDocumento = nroDocumento;
        this.sectoresDondeTrabaja = new HashSet<>();
        this.trayectos = new ArrayList<>();
        this.domicilio = hogar;
    }

    public Set<Sector> getSectoresDondeTrabaja() {
        return this.sectoresDondeTrabaja;
    }

    public Set<Organizacion> organizacionesDondeTrabaja(){
        return this.sectoresDondeTrabaja
                .stream()
                .map(sector -> sector.getOrganizacion())
                .collect(Collectors.toSet());
    }

    public String nombreCompleto() {
        return this.nombre + " " + this.apellido;
    }

    public void agregarSector(Sector sector) throws MiembroException {
        if(this.trabajaEnSector(sector))
            throw new MiembroException("El miembro ya trabaja en ese sector");
        this.sectoresDondeTrabaja.add(sector);
    }

    public void quitarSector(Sector sector) throws MiembroException {
        if(!this.trabajaEnSector(sector))
            throw new MiembroException("El miembro no trabaja en ese sector");
        this.sectoresDondeTrabaja.remove(sector);
    }

    public void solicitarIngresoAlSector(Sector sector) throws MiembroException {
        sector.agregarMiembro(this);
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

    public void registrarTrayecto(Trayecto unTrayecto) {
        this.trayectos.add(unTrayecto);
        //registrar en cada organizacion en la que trabaja
//        this.sectoresDondeTrabaja.iterator().next().getOrganizacion().cargarTrayecto(unTrayecto,this);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public TipoDeDocumento getTipoDeDocumento() {
        return tipoDeDocumento;
    }

    public void setTipoDeDocumento(TipoDeDocumento tipoDeDocumento) {
        this.tipoDeDocumento = tipoDeDocumento;
    }

    public Integer getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(int nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public Trayecto getTrayecto(Integer index) {
        return trayectos.get(index - 1);
    }

    public void agregarTrayecto(Trayecto trayecto) {
        trayectos.add(trayecto);
    }

    public Integer cantidadTrayectos() {
        return trayectos.size();
    }

    public List<Trayecto> getTrayectos() {
        return trayectos;
    }
}

