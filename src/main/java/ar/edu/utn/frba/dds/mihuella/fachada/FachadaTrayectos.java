package ar.edu.utn.frba.dds.mihuella.fachada;

import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.MiembroException;
import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.entities.transportes.MedioFactory;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.mihuella.dto.NuevoTrayectoDTO;
import ar.edu.utn.frba.dds.mihuella.dto.TrayectoCompartidoDTO;
import ar.edu.utn.frba.dds.mihuella.parsers.NoExisteMedioException;
import ar.edu.utn.frba.dds.repositories.daos.EntityManagerHelper;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;

import java.util.List;
import java.util.NoSuchElementException;

public class FachadaTrayectos {
    private final Repositorio<Miembro> repoMiembros;
    private final Repositorio<Trayecto> repoTrayectos;
    private final Repositorio<MedioDeTransporte> repoMedios;

    public FachadaTrayectos() {
        this.repoMiembros = FactoryRepositorio.get(Miembro.class);
        this.repoTrayectos = FactoryRepositorio.get(Trayecto.class);
        this.repoMedios = FactoryRepositorio.get(MedioDeTransporte.class);
    }

    public FachadaTrayectos(Repositorio<Miembro> repoMiembros, Repositorio<Trayecto> repoTrayectos, Repositorio<MedioDeTransporte> repoMedios) {
        this.repoMiembros = repoMiembros;
        this.repoTrayectos = repoTrayectos;
        this.repoMedios = repoMedios;
    }

    public void cargarTrayecto(Trayecto unTrayecto) {
        this.repoTrayectos.agregar(unTrayecto); //TODO ver si validar que los demas repos tengan los datos del trayecto (miembros por ej)
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
        Miembro unMiembro = repoMiembros.buscarTodos().stream()
                .filter(mi -> mi.getNroDocumento().equals(trayectoDTO.getMiembroDNI()))
                .findFirst().orElseThrow(MiembroException::new);

        Trayecto trayecto = repoTrayectos.buscarTodos().stream()
                .filter(tr -> tr.getCompartido().equals(trayectoDTO.getTrayectoId()))
                .findFirst().orElseGet(() -> {
                    Periodo periodo = trayectoDTO.getPeriodicidad().equals('A')
                            ? new Periodo(trayectoDTO.getAnio())
                            : new Periodo(trayectoDTO.getAnio(), trayectoDTO.getMes());
                    Trayecto nuevoTrayecto = new Trayecto(periodo);
                    System.out.println(Math.toIntExact(trayectoDTO.getTrayectoId()));
                    nuevoTrayecto.setCompartido(trayectoDTO.getTrayectoId());
//                    repoTrayectos.modificar(nuevoTrayecto);
                    repoTrayectos.agregar(nuevoTrayecto);
//                    EntityManagerHelper.getEntityManager().getTransaction().begin();
//                    Trayecto mgTrayecto = EntityManagerHelper.getEntityManager().merge(nuevoTrayecto);
//                    EntityManagerHelper.getEntityManager().persist(mgTrayecto);
//                    EntityManagerHelper.getEntityManager().getTransaction().commit();

                    unMiembro.agregarTrayecto(nuevoTrayecto);
                    return nuevoTrayecto;
                });
        trayecto.agregarmiembro(unMiembro);

        MedioDeTransporte medioSolicitado = new MedioFactory()
                .getMedioDeTransporte(trayectoDTO.getTipoMedio(), trayectoDTO.getAtributo1(), trayectoDTO.getAtributo2());
        MedioDeTransporte medio = repoMedios.buscarTodos().stream()
                .filter((me) -> me.equals(medioSolicitado)).findFirst()
                .orElseThrow(() -> new NoExisteMedioException(medioSolicitado));

        Coordenada coordenadaInicial = new Coordenada(trayectoDTO.getLatitudInicial(), trayectoDTO.getLongitudInicial());
        Coordenada coordenadaFinal = new Coordenada(trayectoDTO.getLatitudFinal(), trayectoDTO.getLongitudFinal());
        UbicacionGeografica ubicacionInicial = new UbicacionGeografica(coordenadaInicial);
        UbicacionGeografica ubicacionFinal = new UbicacionGeografica(coordenadaFinal);
        Tramo tramo = new Tramo(medio, ubicacionInicial, ubicacionFinal);
        tramo.setValor(10F);
        tramo.setTrayecto(trayecto);
        trayecto.agregarTramo(tramo);
        repoTrayectos.modificar(trayecto);
    }

    public void cargarTrayectoPasivo(TrayectoCompartidoDTO trayectoCompartidoDTO) {
        Miembro miembro = repoMiembros.buscarTodos().stream()
                .filter(mi -> mi.getNroDocumento().equals(trayectoCompartidoDTO.getMiembroDNI()))
                .findFirst().orElseThrow(() ->
                    new NoSuchElementException("El miembro con DNI: " + trayectoCompartidoDTO.getMiembroDNI() + "no existe en el sistema")
                );

        // Si da error el get es porque se intentó referenciar con un trayecto
        // compartido a un lider de trayecto que no existe
        Trayecto trayecto = repoTrayectos.buscarTodos().stream()
                .filter(tr -> tr.getCompartido().equals(trayectoCompartidoDTO.getTrayectoReferencia()))
                .findFirst().orElseThrow(() ->
                        new NoExisteTrayectoCompartidoException(trayectoCompartidoDTO.getTrayectoReferencia())
                );

        trayecto.agregarmiembro(miembro);
        miembro.agregarTrayecto(trayecto);
    }
}
