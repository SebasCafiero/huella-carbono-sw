package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.entities.lugares.ClasificacionOrganizacion;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.lugares.TipoDeOrganizacionEnum;
import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.entities.mediciones.ReporteOrganizacion;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.entities.transportes.*;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaOrganizacion;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaReportes;
import ar.edu.utn.frba.dds.repositories.RepoFactores;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import ar.edu.utn.frba.dds.server.SystemProperties;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;

import static java.lang.System.exit;

public class PruebasJPA {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        UbicacionGeografica utnCampus = new UbicacionGeografica(new Coordenada(-34.659932F, -58.468397F));
        UbicacionGeografica mirallaAlberdi = new UbicacionGeografica(new Coordenada(-34.649292F, -58.499945F));
        UbicacionGeografica sanPedrito = new UbicacionGeografica(new Coordenada(-34.630861F, -58.470063F));
        UbicacionGeografica castroBarros = new UbicacionGeografica(new Coordenada(-34.611624F, -58.421263F));
        UbicacionGeografica utnMedrano = new UbicacionGeografica(new Coordenada(-34.598412F, -58.420196F));

        MedioDeTransporte fitito = new VehiculoParticular(TipoVehiculo.AUTOMOVIL, TipoCombustible.GNC);

        TransportePublico colectivo63 = new TransportePublico(TipoTransportePublico.COLECTIVO, "63");
        colectivo63.agregarParadas(
                new Parada(new Coordenada(-34.655186F, -58.506615F), 0F, 0.6F), // pilar
                new Parada(new Coordenada(-34.651554F, -58.502202F), 0.6F, 0.350F), // larrazabal
                new Parada(mirallaAlberdi, 0.350F, 0.6F), // miralla
                new Parada(new Coordenada(-34.645056F, -58.495307F), 0.6F, 1.3F), // escalada
                new Parada(new Coordenada(-34.636489F, -58.492135F), 1.5F, 0.950F), // mozart
                new Parada(new Coordenada(-34.634149F, -58.482348F), 0.950F, 1.1F), // mariano acosta
                new Parada(sanPedrito, 1.1F, 0.8F), // san pedrito
                new Parada(new Coordenada(-34.626601F, -58.471626F), 0.6F, 0.7F), // avellaneda
                new Parada(new Coordenada(-34.620776F, -58.474034F), 0.7F, 0F) // gaona
        );

        // 0.6 + 1.3 + 0.95 + 1.1 = 3.9 * 200

        TransportePublico subteA = new TransportePublico(TipoTransportePublico.SUBTE, "A");
        subteA.agregarParadas(
                new Parada(sanPedrito, 0F, 1.3F), // san pedrito
                new Parada(new Coordenada(-34.626615F, -58.456582F), 1.3F, 1.5F), // carabobo
                new Parada(new Coordenada(-34.620974F, -58.442162F), 1.5F, 0.6F), // primera junta
                new Parada(new Coordenada(-34.617957F, -58.435755F), 0.6F, 0.5F), // acoyte
                new Parada(castroBarros, 0.5F, 0.7F), // castro barros
                new Parada(new Coordenada(-34.610828F, -58.415497F), 0.7F, 0.7F), // loria
                new Parada(new Coordenada(-34.610144F, -58.406930F), 0.7F, 0F) // plaza miserere
        );

        // 1.3 + 1.5 + 0.6 + 0.5 = 3.9 * 125

        MedioDeTransporte caminata = new TransporteEcologico(TipoTransporteEcologico.PIE);

        Tramo fiat600 = new Tramo(fitito, utnCampus, mirallaAlberdi);
        fiat600.setValor();
        Tramo bondi = new Tramo(colectivo63, mirallaAlberdi, sanPedrito);
        bondi.setValor();
        Tramo subte = new Tramo(subteA, sanPedrito, castroBarros);
        subte.setValor();
        Tramo aPata = new Tramo(caminata, castroBarros, utnMedrano);
        aPata.setValor();

        Trayecto trayecto1 = new Trayecto(new Periodo(2022));
        trayecto1.agregarTramos(Arrays.asList(fiat600, bondi, subte, aPata));
        fiat600.setTrayecto(trayecto1);
        bondi.setTrayecto(trayecto1);
        subte.setTrayecto(trayecto1);
        aPata.setTrayecto(trayecto1);

        Repositorio<MedioDeTransporte> repoMedios = FactoryRepositorio.get(MedioDeTransporte.class);
        repoMedios.agregar(fitito);
        repoMedios.agregar(colectivo63);
        repoMedios.agregar(subteA);
        repoMedios.agregar(caminata);

        Repositorio<Trayecto> repoTrayectos = FactoryRepositorio.get(Trayecto.class);
        repoTrayectos.agregar(trayecto1);

        FachadaOrganizacion fachadaOrganizacion = new FachadaOrganizacion();

        Organizacion unaOrg = new Organizacion("UTN - Campus",
                TipoDeOrganizacionEnum.EMPRESA,
                new ClasificacionOrganizacion("Educativa"),
                utnCampus);

        Sector unSector = new Sector("miSector", unaOrg);

        Miembro miembro1 = new Miembro("jose", "pepito", TipoDeDocumento.DNI, 12345);
        miembro1.agregarTrayecto(trayecto1);
        trayecto1.agregarMiembro(miembro1);

        miembro1.solicitarIngresoAlSector(unSector);

        Repositorio<Organizacion> repoOrganizaciones = FactoryRepositorio.get(Organizacion.class);
        repoOrganizaciones.agregar(unaOrg);

        if(!SystemProperties.isJpa()) {
            Repositorio<Miembro> repoMiembros = FactoryRepositorio.get(Miembro.class);
            repoMiembros.agregar(miembro1);
        }

        inicializarFactores();

        String archivoSalida = SystemProperties.isJpa() ? "resources/salida-jpa.csv" : "resources/salida-mem.csv";

        PrintWriter writer = new PrintWriter(archivoSalida, "UTF-8");

        Periodo periodo = new Periodo(2022);

        FachadaReportes fachadaReportes = new FachadaReportes();

        ReporteOrganizacion reporteOrganizacion = fachadaReportes.generarReporteOrganizacion(unaOrg, periodo);

        String mensaje1 = "Total : " + reporteOrganizacion.getConsumoTotal();
        writer.println(mensaje1);
        System.out.println(mensaje1  + '\n');

        String mensaje2 = "Total mediciones : " + reporteOrganizacion.getConsumoMediciones();
        writer.println(mensaje2);
        System.out.println(mensaje2  + '\n');

        for (Map.Entry<Miembro, Float> miembro : reporteOrganizacion.getConsumoPorMiembro().entrySet()) {
            String mensaje = "Miembro : " + miembro.getKey().getNroDocumento() + " :-> " + miembro.getValue();
            writer.println(mensaje);
            System.out.println(mensaje  + '\n');
        }

        for (Map.Entry<Sector, Float> sector : reporteOrganizacion.getConsumoPorSector().entrySet()) {
            String mensaje = "Sector : " + sector.getKey().getNombre() + " :-> " + sector.getValue();
            writer.println(mensaje);
            System.out.println(mensaje  + '\n');
        }

        for(Map.Entry<Categoria, Float> categoria : reporteOrganizacion.getConsumoPorCategoria().entrySet()) {
            String mensaje = "Categoria : " + categoria.getKey().toString() + " :-> " + categoria.getValue();
            writer.println(mensaje);
            System.out.println(mensaje  + '\n');
        }

        writer.close();
        exit(0);
    }

    static void inicializarFactores() {
        RepoFactores repoFactores = (RepoFactores) FactoryRepositorio.get(FactorEmision.class);
        repoFactores.agregar(new FactorEmision(new Categoria("Combustion Fija", "Gas Natural"), "m3", 1F));
        repoFactores.agregar(new FactorEmision(new Categoria("Combustion Fija", "Diesel"), "lt", 2F));
        repoFactores.agregar(new FactorEmision(new Categoria("Combustion Movil", "Gasoil"), "lt", 3F));
        repoFactores.agregar(new FactorEmision(new Categoria("Electricidad", "Electricidad"), "kw", 4F));

        repoFactores.agregar(new FactorEmision(new Categoria("Traslado de Miembros", "Particular - GNC"), "km", 100F));
        repoFactores.agregar(new FactorEmision(new Categoria("Traslado de Miembros", "Particular - GASOIL"), "km", 80F));
        repoFactores.agregar(new FactorEmision(new Categoria("Traslado de Miembros", "Particular - NAFTA"), "km", 150F));
        repoFactores.agregar(new FactorEmision(new Categoria("Traslado de Miembros", "Particular - ELECTRICO"), "km", 15F));

        repoFactores.agregar(new FactorEmision(new Categoria("Traslado de Miembros", "Publico - TREN"), "km", 250F));
        repoFactores.agregar(new FactorEmision(new Categoria("Traslado de Miembros", "Publico - SUBTE"), "km", 125F));
        repoFactores.agregar(new FactorEmision(new Categoria("Traslado de Miembros", "Publico - COLECTIVO"), "km", 200F));

        repoFactores.agregar(new FactorEmision(new Categoria("Traslado de Miembros", "Ecologico"), "km", 0F));

        repoFactores.agregar(new FactorEmision(new Categoria("Traslado de Miembros", "Contratado"), "km", 130F));
    }
}