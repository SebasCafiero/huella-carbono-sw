package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.entities.mediciones.ReporteOrganizacion;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaReportes;
import ar.edu.utn.frba.dds.repositories.RepoOrganizaciones;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.testMemoData.SetupInicialJPA;
import ar.edu.utn.frba.dds.server.SystemProperties;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import static java.lang.System.exit;

public class SetupDataInicial {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

        SetupInicialJPA setup = new SetupInicialJPA();
//        setup.undoSetup();
        setup.doSetup();

        String archivoSalida = SystemProperties.isJpa() ? "resources/salida-jpa.csv" : "resources/salida-mem.csv";

        PrintWriter writer = new PrintWriter(archivoSalida, "UTF-8");

        Periodo periodo = new Periodo(2022);

        RepoOrganizaciones repoOrganizaciones = (RepoOrganizaciones) FactoryRepositorio.get(Organizacion.class);

        FachadaReportes fachadaReportes = new FachadaReportes();

        Organizacion unaOrg = repoOrganizaciones.findByRazonSocial("UTN - Campus");

        if(unaOrg == null) {
            System.out.println("La org no existe");
            exit(0);
        }

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

}