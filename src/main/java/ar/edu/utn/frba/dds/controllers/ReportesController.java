package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.interfaces.gui.dto.ReporteHBS;
import ar.edu.utn.frba.dds.interfaces.gui.mappers.AgenteMapperHBS;
import ar.edu.utn.frba.dds.interfaces.gui.mappers.OrganizacionMapperHBS;
import ar.edu.utn.frba.dds.interfaces.gui.mappers.ReporteMapperHBS;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.lugares.AreaSectorial;
import ar.edu.utn.frba.dds.entities.medibles.Categoria;
import ar.edu.utn.frba.dds.entities.medibles.Periodo;
import ar.edu.utn.frba.dds.entities.medibles.ReporteAgente;
import ar.edu.utn.frba.dds.entities.medibles.ReporteOrganizacion;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.interfaces.gui.dto.ErrorResponse;
import ar.edu.utn.frba.dds.servicios.fachadas.FachadaReportes;
import ar.edu.utn.frba.dds.repositories.utils.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import org.hibernate.SessionException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReportesController {
    private final FachadaReportes fachadaReportes;
    private final Repositorio<AreaSectorial> repoAreas;
    private final Repositorio<Organizacion> repoOrganizaciones;
    private final Repositorio<AgenteSectorial> repoAgentes;

    public ReportesController() {
        this.fachadaReportes = new FachadaReportes();
        this.repoAreas = FactoryRepositorio.get(AreaSectorial.class);
        this.repoOrganizaciones = FactoryRepositorio.get(Organizacion.class);
        this.repoAgentes = FactoryRepositorio.get(AgenteSectorial.class);
    }

    public Object generarReporteAgente(Request request, Response response) {
        int idArea = Integer.parseInt(request.params(("id")));
        AreaSectorial areaSectorial = repoAreas.buscar(idArea);
        if(areaSectorial == null) {
            response.status(400);
            return new ErrorResponse("La organizacion de id " + idArea + " no existe");
        }
        LocalDate fecha = LocalDate.now();
        return fachadaReportes
                .generarReporteAgente(areaSectorial, fecha.getYear(), fecha.getMonthValue());
    }

    public Object generarReporteOrganizacion(Request request, Response response) {
        int idOrganizacion = Integer.parseInt(request.params(("id")));
        Organizacion organizacion = repoOrganizaciones.buscar(idOrganizacion);
        if(organizacion == null) {
            response.status(400);
            return new ErrorResponse("La organizacion de id " + idOrganizacion + " no existe");
        }
        LocalDate fecha = LocalDate.now();
        return fachadaReportes
                .generarReporteOrganizacion(organizacion, new Periodo(fecha.getYear(), fecha.getMonthValue()));
    }



    public ModelAndView darAltaYMostrar(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();

        Organizacion org;
        Integer idOrg;
        if(request.params("organizacion") != null) {
            idOrg = Integer.parseInt(request.params("organizacion"));
            try {
                org = repoOrganizaciones.buscar(idOrg); //deberia coincidir con los permisos del usuario
            } catch (NullPointerException | SessionException e) {
                response.status(400);
                String errorDesc = "Organización de id " + idOrg + " inexistente";
                if (e instanceof SessionException) {
                    response.status(403);
                    errorDesc = "Acceso no permitido";
                }
                parametros.put("codigo", response.status());
                parametros.put("descripcion", errorDesc);
                return new ErrorResponse(errorDesc).generarVista(parametros);
            }

            parametros.put("rol", "ORGANIZACION");
            parametros.put("user", org.getRazonSocial());
            parametros.put("organizacion", OrganizacionMapperHBS.toDTO(org));
        }

        Integer idAgente;
        AgenteSectorial agente;
        if(request.params("agente") != null) {
            idAgente = Integer.parseInt(request.params("agente"));
            agente = this.repoAgentes.buscar(idAgente);

            parametros.put("rol", "AGENTE"); //todo ver si poner como el menu
            parametros.put("user", agente.getMail().getDireccion()); //todo agregar nombre en agente?
            parametros.put("agente", AgenteMapperHBS.toDTO(agente));
            //todo quizas agregar reporte de agente (todas las organizaciones en uno)
        }

        ReporteOrganizacion reporte = fachadaReportes.getReporteOrganizacion();
        if(reporte != null) {
            parametros.put("reporte", ReporteMapperHBS.toDTO(reporte));
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyyMMdd");

            String file = reporte.getOrganizacion().getRazonSocial().toLowerCase().replaceAll("\\s","") + reporte.getFechaCreacion().format(formato) + ".txt";
            parametros.put("file", file);
//            parametros.put("file", "reporte"+org.getId());

            fachadaReportes.quitarReporteOrganizacion();

            //todo ver session.refresh(entity) o entityManager.refresh(entity) para no usar cache
            //todo https://sparkjava.com/documentation#examples-and-faq
        }

        return new ModelAndView(parametros, "reporte.hbs");
    }

    public Response generar(Request request, Response response) {
        String fechaActual = LocalDate.now().getMonthValue()+"/"+LocalDate.now().getYear();
        String[] fecha = request.queryParamOrDefault("f-fecha", fechaActual).split("/"); //todo validar fecha
        Periodo periodo = new Periodo(Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0]));
        Organizacion organizacion = null;
        String ruta = "";
        if(request.params("organizacion") != null) {
            int idOrg = Integer.parseInt(request.params("organizacion"));

            organizacion = repoOrganizaciones.buscar(idOrg);
            ruta = "/organizacion/"+organizacion.getId()+"/reporte";
        }

        if(request.params("agente") != null) {
            Integer idAgente = Integer.parseInt(request.params("agente"));
            AgenteSectorial agente = this.repoAgentes.buscar(idAgente);
            int idOrg = Integer.parseInt(request.queryParams("f-organizacion"));

            organizacion = repoOrganizaciones.buscar(idOrg);
            ruta = "/agente/"+agente.getId()+"/reporte";
        }
        fachadaReportes.generarReporteOrganizacion2(organizacion, periodo);
        documentarReporte(fachadaReportes.getReporteOrganizacion(), organizacion); //todo no lo toma bien, agarra el viejo o ninguno
        response.redirect(ruta);

        return response;
    }

    private String documentarReporte(ReporteOrganizacion reporte, Organizacion organizacion) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyyMMdd");
        String arch = organizacion.getRazonSocial().toLowerCase().replaceAll("\\s","") + reporte.getFechaCreacion().format(formato) + ".txt";

        try {
            PrintWriter writer = new PrintWriter("resources/public/docs/"+arch, "UTF-8");
            writer.println("Fecha de Creacion: " + reporte.getFechaCreacion());
            writer.println("Periodo de Referencia: " + reporte.getPeriodoReferencia());
            writer.println("Total : " + reporte.getConsumoTotal());
            writer.println("Total mediciones : " + reporte.getConsumoMediciones());

            for (Map.Entry<Miembro, Float> miembro : reporte.getConsumoPorMiembro().entrySet()) {
                writer.println("Miembro : " + miembro.getKey().getNroDocumento() + " :-> " + miembro.getValue());
            }

            for (Map.Entry<Sector, Float> sector : reporte.getConsumoPorSector().entrySet()) {
                writer.println("Sector : " + sector.getKey().getNombre() + " :-> " + sector.getValue());
            }

            for(Map.Entry<Categoria, Float> categoria : reporte.getConsumoPorCategoria().entrySet()) {
                writer.println("Categoria : " + categoria.getKey().toString() + " :-> " + categoria.getValue());
            }

            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        return arch;
    }
}
