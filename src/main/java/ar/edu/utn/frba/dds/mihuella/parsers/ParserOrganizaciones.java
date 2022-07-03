package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.lugares.*;
import ar.edu.utn.frba.dds.personas.Miembro;
import ar.edu.utn.frba.dds.personas.TipoDeDocumento;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ParserOrganizaciones {
    public List<Organizacion> cargarOrganizaciones(String archivo) throws Exception {
        String organizacionesJSON = new JSONParser().parse(new FileReader(archivo)).toString();
        System.out.println("Carga de Organizaciones:\n" + organizacionesJSON);

        List<Organizacion> organizaciones = new ArrayList<Organizacion>();

        JSONArray arrayOrg = new JSONArray(organizacionesJSON);
        for (int orgIndex = 0; orgIndex < arrayOrg.length(); orgIndex++) {
            JSONObject org = arrayOrg.getJSONObject(orgIndex);

            String razonSocial = org.getString("organizacion");
            ClasificacionOrganizacion clasificacion = new ClasificacionOrganizacion(org.getString("clasificacion"));

            if(!Organizacion.existeTipoOrganizacion(org.getString("tipo")))
                throw new Exception("Error en el tipo de la organizacion. No existe el tipo " + org.getString("tipo"));

            TipoDeOrganizacionEnum tipoDeOrganizacion = TipoDeOrganizacionEnum.valueOf(org.getString("tipo"));
            UbicacionGeografica ubicacionOrg = new UbicacionGeografica(org.getString("ubicacion"), org.getFloat("latitud"), org.getFloat("longitud"));

            Organizacion nuevaOrg = new Organizacion(razonSocial, tipoDeOrganizacion, clasificacion, ubicacionOrg);
            organizaciones.add(nuevaOrg);

            JSONArray arraySectores = org.getJSONArray("sectores");
            for (int sectorIndex = 0; sectorIndex < arraySectores.length(); sectorIndex++) {
                JSONObject sector = arraySectores.getJSONObject(sectorIndex);

                String nombreSector = sector.getString("nombre");
                Sector nuevoSector = new Sector(nombreSector, nuevaOrg);

                JSONArray arrayMiembros = sector.getJSONArray("miembros");
                for (int miembroIndex = 0; miembroIndex < arrayMiembros.length(); miembroIndex++) {
                    JSONObject miembro = arrayMiembros.getJSONObject(miembroIndex);

                    String nombreMiembro = miembro.getString("nombre");
                    String apellido = miembro.getString("apellido");
                    TipoDeDocumento tipoDeDocumento = TipoDeDocumento.valueOf(miembro.getString("tipoDocumento"));
                    int documento = miembro.getInt("documento");

                    UbicacionGeografica ubicacionMiembro = new UbicacionGeografica(org.getString("ubicacion"), org.getFloat("latitud"), org.getFloat("longitud"));
                    Miembro nuevoMiembro = new Miembro(nombreMiembro, apellido, tipoDeDocumento, documento, ubicacionMiembro);

                    nuevoSector.agregarMiembro(nuevoMiembro);
                }
            }
        }
        return organizaciones;
    }
}
