package ar.edu.utn.frba.dds.mihuella.fachada;

import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.MiembroException;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.mihuella.dto.NuevoTrayectoDTO;
import ar.edu.utn.frba.dds.mihuella.dto.TrayectoCompartidoDTO;
import ar.edu.utn.frba.dds.repositories.RepoMiembros;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;

import java.util.List;
import java.util.NoSuchElementException;

public class FachadaTrayectos {
    private final RepoMiembros repoMiembros;
    private final Repositorio<Trayecto> repoTrayectos;
    private final Repositorio<MedioDeTransporte> repoMedios;
    private final FachadaMedios fachadaMedios;

    public FachadaTrayectos() {
        this.repoMiembros = (RepoMiembros) FactoryRepositorio.get(Miembro.class);
        this.repoTrayectos = FactoryRepositorio.get(Trayecto.class);
        this.repoMedios = FactoryRepositorio.get(MedioDeTransporte.class);
        this.fachadaMedios = new FachadaMedios();
    }

    public FachadaTrayectos(RepoMiembros repoMiembros, Repositorio<Trayecto> repoTrayectos, Repositorio<MedioDeTransporte> repoMedios) {
        this.repoMiembros = repoMiembros;
        this.repoTrayectos = repoTrayectos;
        this.repoMedios = repoMedios;
        this.fachadaMedios = new FachadaMedios();
    }

    public Trayecto cargarTrayecto(Trayecto unTrayecto) {
        return this.repoTrayectos.agregar(unTrayecto); //TODO ver si validar que los demas repos tengan los datos del trayecto (miembros por ej)
    }

    public List<Trayecto> obtenerTrayectos() {
        return this.repoTrayectos.buscarTodos();
    }

    public Trayecto obtenerTrayecto(Integer id) {
        return this.repoTrayectos.buscar(id);
    }

    public void eliminarTrayecto(Trayecto trayecto) {
        repoTrayectos.eliminar(trayecto);
    }

    public List<Miembro> obtenerMiembros() {
        return this.repoMiembros.buscarTodos();
    }

    public Miembro obtenerMiembro(Integer id) {
        return this.repoMiembros.buscar(id);
    }

    public List<MedioDeTransporte> obtenerTransportes() {
        return this.repoMedios.buscarTodos();
    }

    public MedioDeTransporte obtenerTransporte(Integer id) {
        return this.repoMedios.buscar(id);
    }

    public void modificarTrayecto(Trayecto trayecto) {
        this.repoTrayectos.modificar(trayecto.getId(),trayecto);
    }

    public void mostrarTrayectos() {
        repoTrayectos.buscarTodos().forEach(System.out::println);
    }

    public void cargarTrayectoActivo(NuevoTrayectoDTO trayectoDTO) {
        Miembro unMiembro = repoMiembros.findByDocumento(TipoDeDocumento.valueOf(trayectoDTO.getTipoDocumento()),
                        trayectoDTO.getMiembroDNI())
                .orElseThrow(MiembroException::new);

        Trayecto trayecto = repoTrayectos.buscarTodos().stream()
                .filter(tr -> tr.getId().equals(trayectoDTO.getTrayectoId()))
                .findFirst().orElseGet(() -> {
                    Periodo periodo = trayectoDTO.getPeriodicidad().equals('A')
                            ? new Periodo(trayectoDTO.getAnio())
                            : new Periodo(trayectoDTO.getAnio(), trayectoDTO.getMes());

                    Trayecto nuevoTrayecto = new Trayecto(periodo);
                    unMiembro.agregarTrayecto(nuevoTrayecto);
                    repoTrayectos.agregar(nuevoTrayecto);

                    return nuevoTrayecto;
                });
        trayecto.agregarMiembro(unMiembro);

        MedioDeTransporte medio = fachadaMedios.obtenerMedio(trayectoDTO.getTipoMedio(), trayectoDTO.getAtributo1(), trayectoDTO.getAtributo2());

        if(medio == null) {
            throw new NoExisteMedioException(trayectoDTO.getTipoMedio(), trayectoDTO.getAtributo1(), trayectoDTO.getAtributo2());
        }

        Coordenada coordenadaInicial = new Coordenada(trayectoDTO.getLatitudInicial(), trayectoDTO.getLongitudInicial());
        Coordenada coordenadaFinal = new Coordenada(trayectoDTO.getLatitudFinal(), trayectoDTO.getLongitudFinal());
        UbicacionGeografica ubicacionInicial = new UbicacionGeografica(coordenadaInicial);
        UbicacionGeografica ubicacionFinal = new UbicacionGeografica(coordenadaFinal);

        Tramo tramo = new Tramo(medio, ubicacionInicial, ubicacionFinal);
        tramo.setValor();
        tramo.setTrayecto(trayecto);

        trayecto.agregarTramo(tramo);
        repoTrayectos.modificar(trayecto);
    }

    public void cargarTrayectoPasivo(TrayectoCompartidoDTO trayectoCompartidoDTO) {
        Miembro miembro = repoMiembros.findByDocumento(TipoDeDocumento.DNI, trayectoCompartidoDTO.getMiembroDNI())
                .orElseThrow(() ->
                    new NoSuchElementException("El miembro con DNI: "
                            + trayectoCompartidoDTO.getMiembroDNI() + "no existe en el sistema")
                );

        // Si da error el get es porque se intentó referenciar con un trayecto que no existe
        Trayecto trayecto = repoTrayectos.buscar(trayectoCompartidoDTO.getTrayectoReferencia());
        if(trayecto == null) {
            throw new NoExisteTrayectoCompartidoException(trayectoCompartidoDTO.getTrayectoReferencia());
        }

        trayecto.agregarMiembro(miembro);
        miembro.agregarTrayecto(trayecto);
    }
}
