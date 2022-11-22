package ar.edu.utn.frba.dds.servicios.fachadas;

import ar.edu.utn.frba.dds.entities.exceptions.TrayectoConMiembroRepetidoException;
import ar.edu.utn.frba.dds.entities.exceptions.TrayectoSinMiembrosException;
import ar.edu.utn.frba.dds.entities.lugares.*;
import ar.edu.utn.frba.dds.entities.medibles.Periodo;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.exceptions.MiembroException;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.entities.medibles.Tramo;
import ar.edu.utn.frba.dds.entities.medibles.Trayecto;
import ar.edu.utn.frba.dds.entities.transportes.Parada;
import ar.edu.utn.frba.dds.entities.transportes.TransportePublico;
import ar.edu.utn.frba.dds.interfaces.controllers.TramoSinDistanciaException;
import ar.edu.utn.frba.dds.interfaces.controllers.TrayectoConMiembrosDeDistintaOrganizacionException;
import ar.edu.utn.frba.dds.servicios.fachadas.exceptions.NoExisteMedioException;
import ar.edu.utn.frba.dds.servicios.fachadas.exceptions.NoExisteTrayectoCompartidoException;
import ar.edu.utn.frba.dds.interfaces.input.NuevoTrayectoDTO;
import ar.edu.utn.frba.dds.interfaces.input.TrayectoCompartidoDTO;
import ar.edu.utn.frba.dds.repositories.RepoMiembros;
import ar.edu.utn.frba.dds.repositories.utils.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import spark.QueryParamsMap;
import spark.Request;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FachadaTrayectos {
    private final RepoMiembros repoMiembros;
    private final Repositorio<Trayecto> repoTrayectos;
    private final FachadaMedios fachadaMedios;

    public FachadaTrayectos() {
        this.repoMiembros = (RepoMiembros) FactoryRepositorio.get(Miembro.class);
        this.repoTrayectos = FactoryRepositorio.get(Trayecto.class);
        this.fachadaMedios = new FachadaMedios();
    }

    public FachadaTrayectos(RepoMiembros repoMiembros, Repositorio<Trayecto> repoTrayectos, Repositorio<MedioDeTransporte> repoMedios) {
        this.repoMiembros = repoMiembros;
        this.repoTrayectos = repoTrayectos;
        this.fachadaMedios = new FachadaMedios();
    }

    public Trayecto cargarTrayecto(Trayecto unTrayecto) {
        return this.repoTrayectos.agregar(unTrayecto); //TODO ver si validar que los demas repos tengan los datos del trayecto (miembros por ej)
    }

    public Trayecto updateTrayecto(Trayecto unTrayecto) {
        return this.repoTrayectos.modificar(unTrayecto); //TODO ver si validar que los demas repos tengan los datos del trayecto (miembros por ej)
    }

    public List<Trayecto> obtenerTrayectos() {
        return this.repoTrayectos.buscarTodos();
    }

    public Trayecto obtenerTrayecto(Integer id) {
        return this.repoTrayectos.buscar(id).get();
    }

    public void eliminarTrayecto(Trayecto trayecto) {
        repoTrayectos.eliminar(trayecto);
    }

    public List<Miembro> obtenerMiembros() {
        return this.repoMiembros.buscarTodos();
    }

    public Miembro obtenerMiembro(Integer id) {
        return this.repoMiembros.buscar(id).get();
    }

    public List<MedioDeTransporte> obtenerTransportes() {
        return this.fachadaMedios.findAll();
    }

    public Optional<MedioDeTransporte> obtenerTransporte(Integer id) {
        return this.fachadaMedios.findById(id);
    }

    public void modificarTrayecto(Trayecto trayecto) {
        this.repoTrayectos.modificar(trayecto.getId(),trayecto);
    }

    public void mostrarTrayectos() {
        repoTrayectos.buscarTodos().forEach(System.out::println);
    }

    public Trayecto cargarTrayectoActivo(Miembro miembro, QueryParamsMap queryMap) {
        Periodo periodo = parsearPeriodo(queryMap.value("f-fecha"));
        Trayecto trayecto = new Trayecto(periodo);
        trayecto.setTramos(asignarTramos(queryMap)); //todo la fecha y tramos vacios son solo en este caso, asi que ver de ignorarlos al modificar trayecto

        if(queryMap.value("f-transporte-nuevo") != null) {
            trayecto.agregarTramo(asignarTramoNuevo(queryMap));
        }

        miembro.agregarTrayecto(trayecto);
        trayecto.agregarMiembro(miembro);
        return cargarTrayecto(trayecto);
    }

    public Trayecto cargarTrayectoPasivo(Miembro miembro, Integer idTrayecto) {
        Trayecto trayecto = this.repoTrayectos.buscar(idTrayecto)
                .orElseThrow(EntityNotFoundException::new);

        if(trayecto.getMiembros().stream().map(Miembro::getId).anyMatch(mi -> mi.equals(miembro.getId()))) {
            throw new TrayectoConMiembroRepetidoException();
        }

        trayecto.getMiembros().stream().limit(1) // Hago el limit para que busque solo en el primer miembro
                .flatMap(mi -> mi.getOrganizaciones().stream())
                .filter(org -> miembro.getOrganizaciones().stream().anyMatch(o -> o.equals(org)))
                .findAny().orElseThrow(TrayectoConMiembrosDeDistintaOrganizacionException::new);

        miembro.agregarTrayecto(trayecto);
        trayecto.agregarMiembro(miembro);

        return updateTrayecto(trayecto);
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

        MedioDeTransporte medio = fachadaMedios.find(trayectoDTO.getTipoMedio(),
                        trayectoDTO.getAtributo1(), trayectoDTO.getAtributo2())
                .orElseThrow(() -> new NoExisteMedioException(trayectoDTO.getTipoMedio(),
                        trayectoDTO.getAtributo1(), trayectoDTO.getAtributo2()));

        Coordenada coordenadaInicial = new Coordenada(trayectoDTO.getLatitudInicial(), trayectoDTO.getLongitudInicial());
        Coordenada coordenadaFinal = new Coordenada(trayectoDTO.getLatitudFinal(), trayectoDTO.getLongitudFinal());
        UbicacionGeografica ubicacionInicial = new UbicacionGeografica(new Direccion(), coordenadaInicial);
        UbicacionGeografica ubicacionFinal = new UbicacionGeografica(new Direccion(), coordenadaFinal);

        Tramo tramo = new Tramo(medio, ubicacionInicial, ubicacionFinal);
        tramo.setValor();
        tramo.setTrayecto(trayecto);

        trayecto.agregarTramo(tramo);
        repoTrayectos.modificar(trayecto);
    }

    public void cargarTrayectoPasivo(TrayectoCompartidoDTO trayectoCompartidoDTO) {
        Miembro miembro = repoMiembros.findByDocumento(TipoDeDocumento.DNI, trayectoCompartidoDTO.getMiembroDNI())
                .orElseThrow(() -> new EntityNotFoundException("El miembro con DNI: " +
                        trayectoCompartidoDTO.getMiembroDNI() + "no existe en el sistema"));

        cargarTrayectoPasivo(miembro, trayectoCompartidoDTO.getTrayectoReferencia());
    }

    private UbicacionGeografica obtenerUbicacion(QueryParamsMap map, MedioDeTransporte transporte, Boolean esInicial, Integer posicion) {
        Function<String, Integer> paramToInt = param -> Integer.parseInt(map.value(param));
        Function<String, Float> paramToFloat = param -> Float.parseFloat(map.value(param));

        String pos = posicion == null ? "nueva" : posicion.toString();
        String lugar = esInicial ? "inicial" : "final";

        if(transporte instanceof TransportePublico) {
            return ((TransportePublico) transporte).getParadas().stream()
                    .filter(p -> p.getId().equals(paramToInt.apply("f-transporte-parada-" + lugar + "-" + pos)))
                    .map(Parada::getUbicacion)
                    .findFirst().get();
        } else {
            return new UbicacionGeografica(
                    "Argentina",
                    map.value("f-provincia-" + lugar + "-" + pos),
                    map.value("f-municipio-" + lugar + "-" + pos),
                    map.value("f-localidad-" + lugar + "-" + pos),
                    map.value("f-calle-" + "-" + lugar + pos),
                    paramToInt.apply("f-numero-" + lugar + "-" + pos),
                    new Coordenada(paramToFloat.apply("f-lat-" + lugar + "-" + pos),
                            paramToFloat.apply("f-lon-" + lugar + "-" + pos))
            );
        }
    }

    private List<Tramo> asignarTramos(QueryParamsMap map) {
        Integer limit = Stream.iterate(0, i -> i + 1)
                .filter(i -> map.value("f-transporte-"+i) == null)
                .findFirst().orElse(0);

        return Stream.iterate(0, i -> i + 1)
                .limit(limit)
                .map(i -> {
//                    if((req.queryParams("f-transporte-parada-inicial-" + i) == null) && (req.queryParams("f-lat-inicial-"+i) == null)) {
//                     //Cuando se deja sin modificar el tramo //TODO VER SI ENTRA ALGUNA VEZ, CREO QUE QUEDO VIEJO CUANDO ESTABA EL DISABLED
//                        return trayecto.getTramos().get(i); //TODO orden de la lista (indice es cant?), o buscar id?
//                    }

                    MedioDeTransporte transporte = obtenerTransporte(Integer.parseInt(map.value("f-transporte-" + i))).get();

                    Tramo tramo = new Tramo(transporte, obtenerUbicacion(map, transporte, true, i), obtenerUbicacion(map, transporte, false, i));

                    if(tramo.getUbicacionInicial().getCoordenada().esIgualAOtraCoordenada(tramo.getUbicacionFinal().getCoordenada())) {
                        throw new TramoSinDistanciaException();
                    }

                    tramo.setId(Integer.parseInt(map.value("f-tramo-id-"+i)));
                    return tramo;
                })
                .collect(Collectors.toList());
    }

    private Tramo asignarTramoNuevo(QueryParamsMap map) {
        MedioDeTransporte transporte = obtenerTransporte(Integer.parseInt(map.value("f-transporte-nuevo"))).get();
        return new Tramo(transporte, obtenerUbicacion(map, transporte, true, null), obtenerUbicacion(map, transporte, false, null));
    }

    private Periodo parsearPeriodo(String input) {
        Periodo periodo;
        if(input.matches("\\d+")) {
            periodo = new Periodo(Integer.parseInt(input));
        } else if(input.matches("\\d+/\\d+")) {
            String[] fecha = input.split("/");
            periodo = new Periodo(Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0]));
        } else {
            LocalDate fecha = LocalDate.now();
            periodo = new Periodo(fecha.getYear(), fecha.getMonthValue());
        }
        return periodo;
    }

    public void modificarTrayecto(Miembro miembro, int idTrayecto, QueryParamsMap queryMap) {
        Trayecto trayecto = this.repoTrayectos.buscar(idTrayecto)
                .orElseThrow(EntityNotFoundException::new);

        if(trayecto.getMiembros().stream().noneMatch(m -> m.getId().equals(miembro.getId()))) {
            throw new MiembroException();
        }

        trayecto.setTramos(asignarTramos(queryMap));

        this.updateTrayecto(trayecto);
    }
}
