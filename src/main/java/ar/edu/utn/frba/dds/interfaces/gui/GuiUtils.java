package ar.edu.utn.frba.dds.interfaces.gui;

import ar.edu.utn.frba.dds.server.login.AuthenticationException;
import ar.edu.utn.frba.dds.server.login.User;
import ar.edu.utn.frba.dds.servicios.fachadas.FachadaUsuarios;
import spark.Request;

import java.util.HashMap;
import java.util.Map;

public class GuiUtils {
    public static Map<String, Object> dtoHeader(Request request) {
        User usuario = new FachadaUsuarios().findById(request.session().attribute("idUsuario"))
                .orElseThrow(AuthenticationException::new);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("user", usuario.getUsername());
        parametros.put("rol", usuario.getRolName());
        return parametros;
    }

    public static Map<String, Object> dtoMenu(Request request) {
        User usuario = new FachadaUsuarios().findById(request.session().attribute("idUsuario"))
                .orElseThrow(AuthenticationException::new);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("user", usuario.getUsername());
        parametros.put("rol", usuario.getRolName());
        parametros.put("miembro", usuario.getMiembro() != null ? usuario.getMiembro().getId() : null);
        parametros.put("organizacion", usuario.getOrganizacion() != null ? usuario.getOrganizacion().getId() : null);
        parametros.put("agente", usuario.getAgenteSectorial() != null ? usuario.getAgenteSectorial().getId() : null);
        return parametros;
    }
}
