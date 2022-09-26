package ar.edu.utn.frba.dds.mihuella.fachada;

import ar.edu.utn.frba.dds.entities.lugares.ClasificacionOrganizacion;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.lugares.TipoDeOrganizacionEnum;
import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.entities.transportes.*;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.mihuella.MedicionSinFactorEmisionException;
import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;
import ar.edu.utn.frba.dds.repositories.impl.memory.RepoFactoresMemoria;
import ar.edu.utn.frba.dds.repositories.utils.RepositorioMemoria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

public class FachadaTrayectosTest {

    private static FachadaTrayectos fachadaTrayectos;
    private static FachadaOrganizacion calculadora;

    @BeforeAll
    public static void initialize() {
        fachadaTrayectos = new FachadaTrayectos(
                new RepositorioMemoria<>(new DAOMemoria<>(Miembro.class)),
                new RepositorioMemoria<>(new DAOMemoria<>(Trayecto.class)),
                new RepositorioMemoria<>(new DAOMemoria<>(MedioDeTransporte.class)));

        Map<String, Float> fe = new HashMap<>();
        fe.put("Traslado de Miembros -> Publico - COLECTIVO : lt", 10F);
        calculadora = new FachadaOrganizacion(new RepoFactoresMemoria<>(new DAOMemoria<>(FactorEmision.class)));
        calculadora.cargarParametros(fe);


    }

    @Test
    void calculaCorrectamenteTramoTransportePublicoEnSentidoNormal() {
        List<Medible> mediciones = new ArrayList<>();
        TransportePublico medio = new TransportePublico(TipoTransportePublico.COLECTIVO, "47");
        medio.agregarParadas(new Parada(new Coordenada(1F,1F), 5F, 4F),
                new Parada(new Coordenada(2F,2F), 5F, 4F),
                new Parada(new Coordenada(3F,3F), 5F, 4F),
                new Parada(new Coordenada(4F,4F), 5F, 4F),
                new Parada(new Coordenada(5F,5F), 5F, 4F)
        );

        Tramo tramo = new Tramo(medio,
                new UbicacionGeografica(new Coordenada(2F,2F)),
                new UbicacionGeografica(new Coordenada(4F,4F)));

        mediciones.add(tramo);

        Assertions.assertEquals(80F, calculadora.obtenerHU(mediciones));
    }

    @Test
    void calculaCorrectamenteTramoTransportePublicoEnSentidoInverso() {
        List<Medible> mediciones = new ArrayList<>();
        TransportePublico medio = new TransportePublico(TipoTransportePublico.COLECTIVO, "47");
        medio.agregarParadas(new Parada(new Coordenada(1F,1F), 5F, 4F),
                new Parada(new Coordenada(2F,2F), 5F, 4F),
                new Parada(new Coordenada(3F,3F), 5F, 4F),
                new Parada(new Coordenada(4F,4F), 5F, 4F),
                new Parada(new Coordenada(5F,5F), 5F, 4F)
        );

        Tramo tramo = new Tramo(medio,
                new UbicacionGeografica(new Coordenada(4F,4F)),
                new UbicacionGeografica(new Coordenada(2F,2F)));

        mediciones.add(tramo);

        Assertions.assertEquals(100F, calculadora.obtenerHU(mediciones));
    }

    @Test
    void calculaCorrectamenteTrayectoCompartido() {
        List<Medible> mediciones = new ArrayList<>();
        TransportePublico medio = new TransportePublico(TipoTransportePublico.COLECTIVO, "47");
        medio.agregarParadas(new Parada(new Coordenada(1F,1F), 5F, 4F),
                new Parada(new Coordenada(2F,2F), 5F, 4F),
                new Parada(new Coordenada(3F,3F), 5F, 4F),
                new Parada(new Coordenada(4F,4F), 5F, 4F),
                new Parada(new Coordenada(5F,5F), 5F, 4F)
        );

        Tramo tramo = new Tramo(medio,
                new UbicacionGeografica(new Coordenada(2F,2F)),
                new UbicacionGeografica(new Coordenada(4F,4F)));

        mediciones.add(tramo);

        Trayecto trayecto = new Trayecto(new Periodo(2020, 11));
        trayecto.agregarTramo(tramo);
        Miembro miembro1 = new Miembro("Carlos", "Tevez", TipoDeDocumento.DNI, 23423423);
        Miembro miembro2 = new Miembro("Esteban", "Tevez", TipoDeDocumento.DNI, 23423424);
        miembro1.agregarTrayecto(trayecto);
        trayecto.agregarmiembro(miembro1);
        miembro2.agregarTrayecto(trayecto);
        trayecto.agregarmiembro(miembro2);

        Organizacion organizacion = new Organizacion("UTN", TipoDeOrganizacionEnum.EMPRESA,
                new ClasificacionOrganizacion("Facultad"),
                new UbicacionGeografica(new Coordenada(2F, 2F)));
        Sector sector1 = new Sector("Capital Humano", organizacion, new HashSet<>(Arrays.asList(miembro1, miembro2)));
        miembro1.agregarSector(sector1);
        miembro2.agregarSector(sector1);
        organizacion.agregarSector(sector1);
        Assertions.assertEquals(80F, calculadora.getImpactoOrganizacion(organizacion, 2020, 11));
    }
}
