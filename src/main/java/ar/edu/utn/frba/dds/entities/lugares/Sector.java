package ar.edu.utn.frba.dds.entities.lugares;

import ar.edu.utn.frba.dds.entities.exceptions.MiembroException;
import ar.edu.utn.frba.dds.entities.exceptions.SectorException;
import ar.edu.utn.frba.dds.entities.personas.Miembro;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SECTOR")
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sector_id")
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organizacion_id")
    private Organizacion organizacion;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "MIEMBRO_POR_SECTOR",
            joinColumns = { @JoinColumn(name = "sector_id") },
            inverseJoinColumns = { @JoinColumn(name = "miembro_id") }
    )
    private Set<Miembro> miembros;

    public Sector(String nombre, Organizacion organizacion) throws SectorException {
        this.nombre = nombre;
        organizacion.agregarSector(this);
        this.organizacion = organizacion;
        this.miembros = new HashSet<>();
    }

    public Sector(String nombre, Organizacion organizacion, Set<Miembro> miembros) {
        this.nombre = nombre;
        this.organizacion = organizacion;
        this.miembros = miembros;
    }

    public Sector() {}

    public String getNombre() { return this.nombre; }

    public Set<Miembro> getMiembros() {
        return this.miembros;
    }

    public Organizacion getOrganizacion() {
        return this.organizacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean esMiembro(Miembro miembro) {
        return this.miembros.contains(miembro);
    }

    public void agregarMiembro(Miembro miembro) {
        if(esMiembro(miembro))
            throw new MiembroException("El miembro ya pertenece a este sector");
        miembros.add(miembro);
        miembro.agregarSector(this);
    }

    public void quitarMiembro(Miembro miembro) {
        if(!esMiembro(miembro))
            throw new MiembroException("El miembro no pertenece a este sector");
        miembros.remove(miembro);
        miembro.quitarSector(this);
    }

    public Integer cantidadMiembros() {
        return this.miembros.size();
    }
}
