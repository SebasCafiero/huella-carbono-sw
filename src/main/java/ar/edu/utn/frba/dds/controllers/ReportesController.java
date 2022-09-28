package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.lugares.geografia.AreaSectorial;
import ar.edu.utn.frba.dds.entities.mediciones.ReporteAgente;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaReportes;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import spark.Request;
import spark.Response;

import java.time.LocalDate;

public class ReportesController {
    private FachadaReportes fachadaReportes;
    private Repositorio<AreaSectorial> repoAreas;

    public ReportesController() {
        this.fachadaReportes = new FachadaReportes();
        this.repoAreas = FactoryRepositorio.get(AreaSectorial.class);
    }

    public Object generarReporteAgente(Request request, Response response) {
        AreaSectorial areaSectorial = repoAreas.buscar(Integer.parseInt(request.params(("id"))));
        LocalDate fecha = LocalDate.now();
        ReporteAgente reporte = fachadaReportes.generarReporteAgente(areaSectorial, fecha.getYear(), fecha.getMonthValue());
        return reporte;
    }
}
