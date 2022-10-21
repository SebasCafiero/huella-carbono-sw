package ar.edu.utn.frba.dds.repositories.testMemoData;

import ar.edu.utn.frba.dds.entities.lugares.ClasificacionOrganizacion;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.lugares.TipoDeOrganizacionEnum;
import ar.edu.utn.frba.dds.entities.lugares.geografia.*;
import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.entities.personas.*;
import ar.edu.utn.frba.dds.entities.transportes.*;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.repositories.RepoFactores;
import ar.edu.utn.frba.dds.repositories.RepoOrganizaciones;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

public class SetupInicialJPA {
    private final Repositorio<MedioDeTransporte> repoMedios;
    private final Repositorio<UbicacionGeografica> repoUbicaciones;
    private final RepoOrganizaciones repoOrganizaciones;
    private final Repositorio<Trayecto> repoTrayectos;
    private final Repositorio<AreaSectorial> repoAreas;
    private final Repositorio<AgenteSectorial> repoAgentes;

    public SetupInicialJPA() {
        this.repoMedios = FactoryRepositorio.get(MedioDeTransporte.class);
        this.repoUbicaciones = FactoryRepositorio.get(UbicacionGeografica.class);
        this.repoOrganizaciones = (RepoOrganizaciones) FactoryRepositorio.get(Organizacion.class);
        this.repoTrayectos = FactoryRepositorio.get(Trayecto.class);
        this.repoAreas = FactoryRepositorio.get(AreaSectorial.class);
        this.repoAgentes = FactoryRepositorio.get(AgenteSectorial.class);
    }

    public void doSetup() {
        // Factores de emision

        this.inicializarFactores();

        // Medios de transporte y ubicaciones

        Provincia cabaProvincia = new Provincia("CABA", "Argentina");
        Municipio cabaMunicipio = new Municipio("Ciudad Autonoma de Buenos Aires", cabaProvincia);

        AgenteSectorial carlos = new AgenteSectorial(cabaProvincia, new ContactoMail("uncontacto@gmail.com", "123"), new ContactoTelefono("1155443322"));
        AgenteSectorial esteban = new AgenteSectorial(cabaMunicipio, new ContactoMail("otrocontacto@gmail.com", "321"), new ContactoTelefono("1122334455"));

        UbicacionGeografica ubicacionUtnCampus = new UbicacionGeografica(
                new Direccion(cabaMunicipio, "Ciudad Autonoma de Buenos Aires", "Mozart", 2300),
                new Coordenada(-34.659932F, -58.468397F));
        UbicacionGeografica mirallaAlberdi = new UbicacionGeografica(new Coordenada(-34.649292F, -58.499945F));
        UbicacionGeografica sanPedrito = new UbicacionGeografica(new Coordenada(-34.630861F, -58.470063F));
        UbicacionGeografica castroBarros = new UbicacionGeografica(new Coordenada(-34.611624F, -58.421263F));
        UbicacionGeografica ubicacionUtnMedrano = new UbicacionGeografica(
                new Direccion(cabaMunicipio, "Ciudad Autonoma de Buenos Aires", "Av. Medrano", 951),
                new Coordenada(-34.598412F, -58.420196F));

        this.repoUbicaciones.agregar(ubicacionUtnCampus, mirallaAlberdi, sanPedrito, castroBarros, ubicacionUtnMedrano);
        this.repoAreas.agregar(cabaProvincia, cabaMunicipio);
        this.repoAgentes.agregar(carlos, esteban);

        MedioDeTransporte fitito = new VehiculoParticular(TipoVehiculo.AUTOMOVIL, TipoCombustible.GNC);
        MedioDeTransporte caminata = new TransporteEcologico(TipoTransporteEcologico.PIE);

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

        this.repoMedios.agregar(fitito, colectivo63, subteA, caminata);

        // Organizaciones, sectores y miembros

        Organizacion orgUtnCampus = new Organizacion("UTN - Campus", TipoDeOrganizacionEnum.INSTITUCION,
                new ClasificacionOrganizacion("Universidad"), ubicacionUtnCampus);

        Sector sistemasUtnCampus = new Sector("Sistemas", orgUtnCampus);
        Miembro juanPerez = new Miembro("Juan", "Perez", TipoDeDocumento.DNI, 14432234);
        Miembro fernandoCoppola = new Miembro("Guillermo", "Cóppola", TipoDeDocumento.PASAPORTE, 11332223);
        sistemasUtnCampus.agregarMiembro(juanPerez);
        sistemasUtnCampus.agregarMiembro(fernandoCoppola);

        Sector rrhhUtnCampus = new Sector("Capital Humano", orgUtnCampus);
        Miembro sebaSosa = new Miembro("Sebastián", "Sosa", TipoDeDocumento.DNI, 11233223);
        Miembro martinTagliafico = new Miembro("Martin", "Tagliafico", TipoDeDocumento.DNI, 30324522);
        rrhhUtnCampus.agregarMiembro(sebaSosa);
        rrhhUtnCampus.agregarMiembro(martinTagliafico);

        Organizacion orgUtnMedrano = new Organizacion("UTN - Medrano", TipoDeOrganizacionEnum.INSTITUCION,
                new ClasificacionOrganizacion("Universidad"), ubicacionUtnMedrano);

        Sector tesoreriaUtnMedrano = new Sector("Tesoreria", orgUtnMedrano);
        Miembro laSaeta = new Miembro("Alfredo", "Di Stéfano", TipoDeDocumento.DNI, 2232122);
        Miembro fitoPaez = new Miembro("Roberto", "Páez", TipoDeDocumento.DNI, 34432233);
        tesoreriaUtnMedrano.agregarMiembro(laSaeta);
        tesoreriaUtnMedrano.agregarMiembro(fitoPaez);

        Sector administracionUtnMedrano = new Sector("Administracion", orgUtnMedrano);
        Miembro elVirrey = new Miembro("Carlos", "Bianchi", TipoDeDocumento.DNI, 22222222);
        Miembro elTitan = new Miembro("Martin", "Palermo", TipoDeDocumento.DNI, 30366666);
        administracionUtnMedrano.agregarMiembro(elVirrey);
        administracionUtnMedrano.agregarMiembro(elTitan);

        this.repoOrganizaciones.agregar(orgUtnCampus);
        this.repoOrganizaciones.agregar(orgUtnMedrano);

        Tramo fiat600 = new Tramo(fitito, ubicacionUtnCampus, mirallaAlberdi);
        fiat600.setValor();
        Tramo bondi = new Tramo(colectivo63, mirallaAlberdi, sanPedrito);
        bondi.setValor();
        Tramo subte = new Tramo(subteA, sanPedrito, castroBarros);
        subte.setValor();
        Tramo aPata = new Tramo(caminata, castroBarros, ubicacionUtnMedrano);
        aPata.setValor();

        Trayecto trayecto1 = new Trayecto(new Periodo(2022));
        trayecto1.agregarTramos(Arrays.asList(fiat600, bondi, subte, aPata));
        fiat600.setTrayecto(trayecto1);
        bondi.setTrayecto(trayecto1);
        subte.setTrayecto(trayecto1);
        aPata.setTrayecto(trayecto1);

        juanPerez.agregarTrayecto(trayecto1);
        trayecto1.agregarMiembro(juanPerez);

        this.repoTrayectos.agregar(trayecto1);

    }

    public void undoSetup() {
        Field campo = null;
        try {
            campo = FactoryRepositorio.class.getDeclaredField("repos");
            campo.setAccessible(true);
            HashMap<String, Repositorio> repos = (HashMap<String, Repositorio>) campo.get(null);
            repos.forEach((clase, repo) -> {
                repo.buscarTodos().forEach(repo::eliminar);
            });
            System.out.println(repos.size());
        } catch(NoSuchFieldException | IllegalAccessException e) {
            System.out.println(e.getMessage());
            return;
        } finally {
            if(campo != null) {
                campo.setAccessible(false);
            }
        }

//        this.repoMedios.buscarTodos().forEach(this.repoMedios::eliminar);
//        this.repoOrganizaciones.buscarTodos().forEach(this.repoOrganizaciones::eliminar);
//        this.repoUbicaciones.buscarTodos().forEach(this.repoUbicaciones::eliminar);
    }

    private void inicializarFactores() {
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
