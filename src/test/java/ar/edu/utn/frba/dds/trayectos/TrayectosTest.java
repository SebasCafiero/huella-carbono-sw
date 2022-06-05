package ar.edu.utn.frba.dds.trayectos;

import ar.edu.utn.frba.dds.excepciones.MiembroException;
import ar.edu.utn.frba.dds.excepciones.SectorException;
import ar.edu.utn.frba.dds.lugares.*;
import ar.edu.utn.frba.dds.personas.Miembro;
import ar.edu.utn.frba.dds.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.transportes.Parada;
import ar.edu.utn.frba.dds.transportes.TipoTransportePublico;
import ar.edu.utn.frba.dds.transportes.TransportePublico;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TrayectosTest {

    @BeforeEach
    public void iniciarOrgSectorMiembro() throws SectorException, MiembroException {

    }

    @Test
    public void testCargaDeTrayectosEnOrganizacion() throws SectorException, MiembroException {
        Organizacion unaOrg = new Organizacion("utn", TipoDeOrganizacionEnum.INSTITUCION,new ClasificacionOrganizacion("Universidad"),new UbicacionGeografica("Buenos Aires",new Coordenada(10F,5F)));
        Sector unSector = new Sector("Administracion",unaOrg);
        Miembro unMiembro = new Miembro("Pedrito","Lopez", TipoDeDocumento.DNI,12345,new UbicacionGeografica("Buenos Aires",10F,10F));
        unMiembro.solicitarIngreso(unSector);
        unaOrg.aceptarSolicitud(unMiembro,unSector);


        TransportePublico unTransportePublico = new TransportePublico(TipoTransportePublico.COLECTIVO,"26");
        unTransportePublico.agregarParadas(
                new Parada(new Coordenada(1F,1F),0F,8F),
                new Parada(new Coordenada(3F,7F),8F,18F),
                new Parada(new Coordenada(15F,13F),18F,0F)
        );

        Trayecto unTrayecto = new Trayecto();
        Tramo tramo1 = new Tramo(unTransportePublico,new Coordenada(1F,1F),new Coordenada(3F,7F));
        Tramo tramo2 = new Tramo(unTransportePublico,new Coordenada(3F,7F),new Coordenada(15F,13F));
        unTrayecto.agregarTramo(tramo1);
        unTrayecto.agregarTramo(tramo2);

        unMiembro.registrarTrayecto(unTrayecto);
        Assertions.assertEquals(26,unTrayecto.calcularDistancia());
        Assertions.assertEquals(26,unaOrg.obtenerDistanciaTrayecto());
    }

}
