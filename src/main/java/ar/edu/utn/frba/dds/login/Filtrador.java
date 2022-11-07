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
            response.status(403);
            throw new RuntimeException();
        }

        if( path.equals("/miembro") ){
            System.out.println("Entre al path: "+ path);
        }


    }
}
