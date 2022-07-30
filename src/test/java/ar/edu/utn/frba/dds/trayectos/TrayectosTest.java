package ar.edu.utn.frba.dds.trayectos;

import ar.edu.utn.frba.dds.personas.MiembroException;
import ar.edu.utn.frba.dds.lugares.SectorException;
import ar.edu.utn.frba.dds.lugares.*;
import ar.edu.utn.frba.dds.mihuella.MedicionSinFactorEmisionException;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaOrganizacion;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import ar.edu.utn.frba.dds.personas.Miembro;
import ar.edu.utn.frba.dds.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.transportes.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TrayectosTest {

    @BeforeEach
    public void iniciarOrgSectorMiembro() throws SectorException, MiembroException {

    }

    @Test
    public void testCargaDeTrayectosEnOrganizacion() throws SectorException, MiembroException {
        Organizacion unaOrg = new Organizacion("utn", TipoDeOrganizacionEnum.INSTITUCION,new ClasificacionOrganizacion("Universidad"),new UbicacionGeografica("Buenos Aires",new Coordenada(10F,5F)));
        Sector unSector = new Sector("Administracion",unaOrg);
        Miembro unMiembro = new Miembro("Pedrito","Lopez", TipoDeDocumento.DNI,12345,new UbicacionGeografica("Buenos Aires",10F,10F));
        unMiembro.solicitarIngresoAlSector(unSector);

        TransportePublico unTransportePublico = new TransportePublico(TipoTransportePublico.COLECTIVO,"26");
        unTransportePublico.agregarParadas(
                new Parada("BsAs", new Coordenada(1F,1F),0F,8F),
                new Parada("BsAs", new Coordenada(3F,7F),8F,18F),
                new Parada("BsAs", new Coordenada(15F,13F),18F,0F)
        );

        Trayecto unTrayecto = new Trayecto();
        Tramo tramo1 = new Tramo(unTransportePublico,new Coordenada(1F,1F),new Coordenada(3F,7F));
        Tramo tramo2 = new Tramo(unTransportePublico,new Coordenada(3F,7F),new Coordenada(15F,13F));
        unTrayecto.agregarTramo(tramo1);
        unTrayecto.agregarTramo(tramo2);

        unMiembro.registrarTrayecto(unTrayecto);
        Assertions.assertEquals(26,unTrayecto.calcularDistancia());
//        Assertions.assertEquals(26,unaOrg.obtenerDistanciaTrayecto());
    }

    @Test
    public void testCargaDeTrayectosCompartidos() throws MedicionSinFactorEmisionException {
        UbicacionGeografica ubicacionHogar = new UbicacionGeografica("Buenos Aires",0F,0F);
//        UbicacionGeografica ubicacionOrg = new UbicacionGeografica("Buenos Aires",10F,15F);
//        Organizacion unaOrg = new Organizacion("utn",TipoDeOrganizacionEnum.INSTITUCION, new ClasificacionOrganizacion("Universidad"),ubicacionOrg);

        Miembro miembro1 = new Miembro("m1","m1",TipoDeDocumento.DNI,1,ubicacionHogar);
        Miembro miembro2 = new Miembro("m2","m2",TipoDeDocumento.DNI,2,ubicacionHogar);
        Miembro miembro3 = new Miembro("m3","m3",TipoDeDocumento.DNI,3,ubicacionHogar);

        TransportePublico unTransportePublico = new TransportePublico(TipoTransportePublico.TREN,"Mitre");
        unTransportePublico.agregarParada(new Parada("BsAs", new Coordenada(8F,15F),22F,13F)); //Parada de mas
        unTransportePublico.agregarParada(new Parada("BsAs", new Coordenada(5F,5F),13F,20F));
        unTransportePublico.agregarParada(new Parada("BsAs", new Coordenada(20F,10F),20F,30F));
        unTransportePublico.agregarParada(new Parada("BsAs", new Coordenada(30F,30F),30F,26F)); //Parada de mas
        TransporteEcologico unaCaminata = new TransporteEcologico(TipoTransporteEcologico.PIE);
        ServicioContratado unServicioContratado = new ServicioContratado(new TipoServicio("Taxi"));

        Tramo tramo1 = new Tramo(unaCaminata, new Coordenada(0F, 0F), new Coordenada(5F,5F));
        Tramo tramo2 = new Tramo(unTransportePublico, new Coordenada(5F,5F),new Coordenada(20F,10F));
        Tramo tramo3 = new Tramo(unTransportePublico, new Coordenada(20F,10F),new Coordenada(30F,30F));
        Tramo tramo4 = new Tramo(unServicioContratado, new Coordenada(30F,30F),new Coordenada(10F,15F));
        Trayecto trayectoCompartido = new Trayecto(tramo1, tramo2, tramo3, tramo4);
        trayectoCompartido.agregarmiembro(miembro1);
        trayectoCompartido.agregarmiembro(miembro2);
        trayectoCompartido.agregarmiembro(miembro3);

        FachadaOrganizacion fachada = new FachadaOrganizacion();
        fachada.setFactorEmision("Traslado de Miembros - Publico - TREN",250F);
        fachada.setFactorEmision("Traslado de Miembros - Contratado", 130F);
        fachada.setFactorEmision("Traslado de Miembros - Ecologico",0F);

//        List<Medible> tramosTotales = unaOrg.miembros().stream().flatMap(m->m.getTrayectos().stream().flatMap(tray->tray.getTramos().stream())).collect(Collectors.toList());

        List<Medible> tramosTotales = new ArrayList<>();
        tramosTotales.add(tramo1);
        tramosTotales.add(tramo2);
        tramosTotales.add(tramo3);
        tramosTotales.add(tramo4);

        Float hu = fachada.obtenerHU(tramosTotales)/trayectoCompartido.cantidadDeMiembros();
        Float distanciaTotal = tramosTotales.stream().map(tr -> tr.getValor()).reduce(0F,(d1,d2)->d1+d2);
        Assertions.assertEquals(10+20+30+35, distanciaTotal);
        Assertions.assertEquals((0+5000+7500+4550)/3F, hu);
    }

}
