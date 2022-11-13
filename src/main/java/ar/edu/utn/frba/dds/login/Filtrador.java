package ar.edu.utn.frba.dds.login;

import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class Filtrador {

    public static void filtrarMiembro(){}

    private static boolean filtrarPorRol(String rolDeAceptacion, String rolProvisto) {
        return rolDeAceptacion.equals(rolProvisto);
    }

    public static void filtrarPorRol(Request req, Response res, String rolDeAceptacion) throws ForbiddenException{
        UserUtils userUtils = new UserUtils();
        String rol = userUtils.getUsuarioLogueado(req.session().attribute("idUsuario")).get().getRol();
        if(!rolDeAceptacion.equals(rol)) {
            res.status(403);
            throw new ForbiddenException();
        };
    }

    public static void filtrarLogueo(Request req, Response res) throws UnauthorizedException{
        UserUtils userUtils = new UserUtils();
        if(!userUtils.estaLogueado(req)){
            res.status(401);
            throw new UnauthorizedException();
        }
    }

    public static void filtrarPorId(Request req, Response res, Integer idDeAceptacion, String rol) throws ForbiddenException{

        UserUtils userUtils = new UserUtils();
        Integer id = null;

        switch(rol) {
            case "miembro":
                id = userUtils.getUsuarioLogueado(req.session().attribute("idUsuario")).get().getMiembro().getId();
                break;
            case "organizacion":
                id = userUtils.getUsuarioLogueado(req.session().attribute("idUsuario")).get().getOrganizacion().getId();
                break;
            case "agente":
                id = userUtils.getUsuarioLogueado(req.session().attribute("idUsuario")).get().getAgenteSectorial().getId();
                break;
            default:
                break;
        }

        if (!idDeAceptacion.equals(id)){
            res.status(403);
            throw new ForbiddenException();
        }
    }

    public static <T> void filtrarExistenciaRecurso(Request req, Response res, Class<T> type, int idRecurso) throws NotFoundException{
        Repositorio<T> repositorio = FactoryRepositorio.get(type);

        if(repositorio.buscar(idRecurso) == null){
            res.status(404);
            throw new NotFoundException();
        }
    }
}
