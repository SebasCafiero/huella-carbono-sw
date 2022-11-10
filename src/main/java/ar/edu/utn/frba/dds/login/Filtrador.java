package ar.edu.utn.frba.dds.login;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class Filtrador {

    public static void filtrarMiembro(){

    }

    public static void filtrar(Request request, Response response) throws RuntimeException {
        String path = request.pathInfo();
        UserUtils userUtils = new UserUtils();

        if (path.equals("/home")){
            return;
        }

        if(!userUtils.estaLogueado(request)){
            response.status(401);
            throw new RuntimeException();
        }

        String rol = userRepository.getRol(request.session().id());

        if(path.equals("/miembro") ||
                path.equals("/organizacion") ||
                path.equals("/medicion") ||
                path.equals("/batchMedicion") ||
                path.equals("/factorEmision") ||
                path.equals("/agenteSectorial") ||
                path.equals("/trayectos") ||
                path.equals("/reportes") ||
                path.equals("/reinicia")){
            if (!filtrarPorRol("admin",rol){
                response.status(403);
                throw new RuntimeException();
            }
            return;
        }
        if()
    }

    private static boolean filtrarPorRol(String rolDeAceptacion, String rolProvisto){
        return rolDeAceptacion.equals(rolProvisto);
    }
    public static void filtrarPorRol(Request req, Response res, String rolDeAceptacion) throws ForbiddenException{
        String rol = userRepository.getRol(req.session().id());
        if(!rolDeAceptacion.equals(rol)) {
            res.status(403);
            throw new ForbiddenException();
        };
    }
    private static boolean filtrarPorId(int idDeAceptacion, int idProvista) throws ForbiddenException{
        return idDeAceptacion == idProvista;
    }
    private static void filtrarPorId(Request req, Response res, int idDeAceptacion) throws UnauthorizedException{

        if (!String.valueOf(idDeAceptacion).equals(req.session().id())){
            res.status(403);
            throw new UnauthorizedException();
        }
    }
}
