package ar.edu.utn.frba.dds.entities.lugares.trayectos;

import ar.edu.utn.frba.dds.entities.lugares.*;
import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.entities.personas.MiembroException;
import ar.edu.utn.frba.dds.entities.transportes.*;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.mihuella.MedicionSinFactorEmisionException;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaOrganizacion;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;
import ar.edu.utn.frba.dds.repositories.impl.memory.RepoFactoresMemoria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrayectosTest {

    @BeforeEach
    public void iniciarOrgSectorMiembro() {

    }

    @Test
    public void testCargaDeTrayectosEnOrganizacion() {
        Organizacion unaOrg = new Organizacion("utn", TipoDeOrganizacionEnum.INSTITUCION, new ClasificacionOrganizacion("Universidad"), new UbicacionGeografica(new Coordenada(10F,5F)));
        Sector unSector = new Sector("Administracion", unaOrg);
        Miembro unMiembro = new Miembro("Pedrito","Lopez", TipoDeDocumento.DNI,12345);
        unMiembro.solicitarIngresoAlSector(unSector);

        TransportePublico unTransportePublico = new TransportePublico(TipoTransportePublico.COLECTIVO,"26");
        unTransportePublico.agregarParadas(
                new Parada(new Coordenada(1F,1F),0F,8F),
                new Parada(new Coordenada(3F,7F),8F,18F),
                new Parada(new Coordenada(15F,13F),18F,0F)
        );

        Trayecto unTrayecto = new Trayecto();
        Tramo tramo1 = new Tramo(unTransportePublico, new Coordenada(1F,1F), new Coordenada(3F,7F));
        Tramo tramo2 = new Tramo(unTransportePublico, new Coordenada(3F,7F), new Coordenada(15F,13F));
        unTrayecto.agregarTramo(tramo1);
        unTrayecto.agregarTramo(tramo2);

        unMiembro.agregarTrayecto(unTrayecto);
        Assertions.assertEquals(26, unTrayecto.calcularDistancia());
    }

    @Test
    public void testCargaDeTrayectosCompartidos() {
        UbicacionGeografica ubicacionHogar = new UbicacionGeografica(new Coordenada(0F,0F));
//        UbicacionGeografica ubicacionOrg = new UbicacionGeografica("Buenos Aires",10F,15F);
//        Organizacion unaOrg = new Organizacion("utn",TipoDeOrganizacionEnum.INSTITUCION, new ClasificacionOrganizacion("Universidad"),ubicacionOrg);

        Miembro miembro1 = new Miembro("m1","m1", TipoDeDocumento.DNI,1);
        Miembro miembro2 = new Miembro("m2","m2", TipoDeDocumento.DNI,2);
        Miembro miembro3 = new Miembro("m3","m3", TipoDeDocumento.DNI,3);

        TransportePublico unTransportePublico = new TransportePublico(TipoTransportePublico.TREN,"Mitre");
        unTransportePublico.agregarParada(new Parada(new Coordenada(8F,15F),22F,13F)); //Parada de mas
        unTransportePublico.agregarParada(new Parada(new Coordenada(5F,5F),13F,20F));
        unTransportePublico.agregarParada(new Parada(new Coordenada(20F,10F),20F,30F));
        unTransportePublico.agregarParada(new Parada(new Coordenada(30F,30F),30F,26F)); //Parada de mas

        TransporteEcologico unaCaminata = new TransporteEcologico(TipoTransporteEcologico.PIE);
        ServicioContratado unServicioContratado = new ServicioContratado(new TipoServicio("Taxi"));

        Tramo tramo1 = new Tramo(unaCaminata, new Coordenada(0F, 0F), new Coordenada(5F,5F));
        Tramo tramo2 = new Tramo(unTransportePublico, new Coordenada(5F,5F),new Coordenada(20F,10F));
        Tramo tramo3 = new Tramo(unTransportePublico, new Coordenada(20F,10F),new Coordenada(30F,30F));
        Tramo tramo4 = new Tramo(unServicioContratado, new Coordenada(30F,30F),new Coordenada(10F,15F));

        Trayecto trayectoCompartido = new Trayecto();
        trayectoCompartido.setTramos(Arrays.asList(tramo1, tramo2, tramo3, tramo4));
        trayectoCompartido.setMiembros(Arrays.asList(miembro1, miembro2, miembro3));

        FachadaOrganizacion fachada = new FachadaOrganizacion(new RepoFactoresMemoria<>(new DAOMemoria<>(FactorEmision.class)));
        fachada.cargarParametro("Traslado de Miembros -> Publico - TREN : km",250F);
        fachada.cargarParametro("Traslado de Miembros -> Contratado : km", 130F);
        fachada.cargarParametro("Traslado de Miembros -> Ecologico : km",0F);

//        List<Medible> tramosTotales = unaOrg.miembros().stream().flatMap(m->m.getTrayectos().stream().flatMap(tray->tray.getTramos().stream())).collect(Collectors.toList());

        List<Medible> tramosTotales = Arrays.asList(tramo1, tramo2, tramo3, tramo4);

        Float hu = fachada.obtenerHU(tramosTotales) / trayectoCompartido.cantidadDeMiembros();
        Float distanciaTotal = tramosTotales.stream().map(tr -> tr.getValor()).reduce(0F,(d1,d2)->d1+d2);
        Assertions.assertEquals(10+20+30+35, distanciaTotal);
        Assertions.assertEquals((0+5000+7500+4550)/3F, hu);
    }

}
