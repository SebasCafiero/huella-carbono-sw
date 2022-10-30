package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.login.User;
import ar.edu.utn.frba.dds.login.UserUtils;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HomeController {
    public ModelAndView inicio(Request request, Response response) {
        return new ModelAndView(null, "home.hbs");
    }

    public ModelAndView menu(Request request, Response response) {
        String username = request.session().attribute("currentUser");
//        User user = new UserUtils().buscar(username);
        Map<String, Object> parametros = new HashMap<>();
//        parametros.put("Rol", user.getRol());
//        parametros.put("User", user.getInfo()); //NombreApellido, RazonSocial, según ROL

        int id;
//        id = user.getRolId(); // id del miembro, de la org o del agente
        id = 2;

        String rol;
//        rol = user.getRol(); //miembro, organizacion o agente
        rol = "miembro";
//        rol = "organizacion";
//        rol = "agente";

        String name;
//        name = user.getName();
        name = "LEO MESSI";
//        name = "UTN";
//        name = "LIONEL SCALONI";

        parametros.put("rol", rol.toUpperCase(Locale.ROOT));
        parametros.put(rol, id);
        parametros.put("user", name);

//        parametros.put("rol","MIEMBRO");
//        parametros.put("miembro", 555);
//        parametros.put("user","LEO MESSI");

        /*parametros.put("rol","ORGANIZACION");
        parametros.put("organizacion", 15);
        parametros.put("user","UTN");*/

        return new ModelAndView(parametros, "menu.hbs");
    }

}
